package org.sparrowframework.foundation.core.impl.support;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sparrowframework.foundation.util.ClassUtils;

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

	public final List<Class<?>> getClassList() {
		List<Class<?>> classList = new ArrayList<>();
		try {
			Enumeration<URL> urls = ClassUtils.getClassLoader().getResources(packageName.replace(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				String protocol = url.getProtocol();

				if ("file".equals("protocol")) {
					String packagePath = url.getPath().replaceAll("%20", " ");
					addClass(classList, packagePath, packageName);
				} else if ("jar".equals(protocol)) {
					JarURLConnection jarFileConnection = (JarURLConnection) url.openConnection();
					JarFile jarFile = jarFileConnection.getJarFile();
					Enumeration<JarEntry> jarEntries = jarFile.entries();
					while (jarEntries.hasMoreElements()) {
						JarEntry jarEntry = jarEntries.nextElement();
						String jarEntryName = jarEntry.getName();
						if (jarEntryName.endsWith(".class")) {
							String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
							doAddClass(classList, className);
						}
					}
				}
			}
		} catch (IOException | ClassNotFoundException ex) {
			LOGGER.error("Failed to load class list!", ex);
		}
		return classList;
	}

	private void addClass(List<Class<?>> classList, String packagePath, String packageName)
			throws ClassNotFoundException {
		File[] files = new File(packagePath).listFiles((file) -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());

		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtils.isNoneBlank(packageName) && StringUtils.isNoneBlank(className)) {
					className = packageName + "." + className;
				}
				doAddClass(classList, className);
			} else {
				String subPackagePath = fileName;
				if (StringUtils.isNotBlank(packagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}

				String subPackageName = fileName;
				if (StringUtils.isNoneBlank(packageName)) {
					subPackageName = packageName + "." + subPackageName;
				}
				addClass(classList, subPackagePath, subPackageName);
			}
		}
	}

	private void doAddClass(List<Class<?>> classList, String className) throws ClassNotFoundException {
		Class<?> cls = ClassUtils.loadClass(className);
		if (checkIfAddClass(cls)) {
			classList.add(cls);
		}
	}

	public abstract boolean checkIfAddClass(Class<?> cls);
}
