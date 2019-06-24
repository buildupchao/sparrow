package org.sparrowframework.foundation.core.impl.support;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.util.ClassUtil;

/**
 * @author buildupchao
 * @date 2019/06/19 16:36
 * @since JDK 1.8
 */
public abstract class ClassScannerSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassScannerSupport.class);

	protected String packageName;

	public ClassScannerSupport(String packageName) {
		super();
		this.packageName = packageName;
	}

	public final Set<Class<?>> getClassSet() {
		Set<Class<?>> classSet = new HashSet<>();
		try {
			Enumeration<URL> urls = ClassUtil.getClassLoader().getResources(packageName.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				String protocol = url.getProtocol();

				if ("file".equals(protocol)) {
					String packagePath = url.getPath().replaceAll("%20", " ");
					addClass(classSet, packagePath, packageName);
				} else if ("jar".equals(protocol)) {
					JarURLConnection jarFileConnection = (JarURLConnection) url.openConnection();
					JarFile jarFile = jarFileConnection.getJarFile();
					Enumeration<JarEntry> jarEntries = jarFile.entries();
					while (jarEntries.hasMoreElements()) {
						JarEntry jarEntry = jarEntries.nextElement();
						String jarEntryName = jarEntry.getName();
						if (jarEntryName.endsWith(".class")) {
							String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
							doAddClass(classSet, className);
						}
					}
				}
			}
		} catch (IOException | ClassNotFoundException ex) {
			LOGGER.error("Failed to load class list!", ex);
		}
		return classSet;
	}

	private void addClass(Set<Class<?>> classSet, String packagePath, String packageName)
			throws ClassNotFoundException {
		File[] files = new File(packagePath).listFiles((file) -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());

		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtils.isNoneBlank(packageName)) {
					className = packageName + "." + className;
				}
				doAddClass(classSet, className);
			} else {
				String subPackagePath = fileName;
				if (StringUtils.isNotBlank(packagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}

				String subPackageName = fileName;
				if (StringUtils.isNoneBlank(packageName)) {
					subPackageName = packageName + "." + subPackageName;
				}
				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}

	private void doAddClass(Set<Class<?>> classSet, String className) throws ClassNotFoundException {
		Class<?> cls = ClassUtil.loadClass(className);
		if (checkIfAddClass(cls)) {
			classSet.add(cls);
		}
	}

	public abstract boolean checkIfAddClass(Class<?> cls);
}
