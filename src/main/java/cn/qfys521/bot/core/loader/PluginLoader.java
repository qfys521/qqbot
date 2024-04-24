/*
 * Copyright (c) qfys521 2024.
 *
 * 本文件 `PluginLoader.java`使用版权 `AGPL-3.0`.
 * 适度编码益脑，沉迷编码伤身，合理安排时间，享受快乐生活。
 *
 * This file `PluginLoader.java` is licensed under the `AGPL-3.0` license.
 * Coding moderately is beneficial to the brain, but overindulgence in coding is harmful to the body. Arrange your time reasonably and enjoy a happy life.
 */

package cn.qfys521.bot.core.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class PluginLoader {

    private final static String PROPERTIES_NAME = "plugin.properties";
    private final static String MAIN_CLASS = "main";

    /**
     * 加载jar文件
     *
     * @param jarFilePath jar文件路径
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Class loadJar(String jarFilePath) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = getClassLoader(jarFilePath);
        Properties properties = getProperties(classLoader, PROPERTIES_NAME);
        String mainClass = properties.getProperty(MAIN_CLASS);
        return loadClass(classLoader, mainClass);
    }

    /**
     * 获得ClassLoader
     *
     * @param jarFilePath jar文件路径
     * @return
     * @throws MalformedURLException
     */
    public static final ClassLoader getClassLoader(String jarFilePath) throws MalformedURLException {
        File jarFile = new File(jarFilePath);
        if (!jarFile.exists()) {
            return null;
        }
        URL url = jarFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, PluginLoader.class.getClassLoader());
        return classLoader;
    }

    /**
     * 获得jar中的properties
     *
     * @param classLoader    classLoader
     * @param propertiesName 文件名称
     * @return
     * @throws IOException
     */
    public static Properties getProperties(ClassLoader classLoader, String propertiesName) throws IOException {
        InputStream propertiesStream = classLoader.getResourceAsStream(propertiesName);
        Properties properties = new Properties();
        properties.load(propertiesStream);
        return properties;
    }

    /**
     * 加载类
     *
     * @param classLoader classLoader
     * @param className   全类名
     * @return
     * @throws ClassNotFoundException
     */
    public static Class loadClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        Class<?> clazz = classLoader.loadClass(className);
        return clazz;
    }

}

