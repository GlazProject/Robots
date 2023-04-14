package ru.projectrobots.resources;

import ru.projectrobots.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

class PropertiesManager {
    private static final Properties knownProperties = new Properties();
    public static final String ENDING = ".properties";

    public static String getProperty(String property, String defaultValue){
        if (knownProperties.size() == 0)
            Logger.error("Properties was not loaded");
        return knownProperties.getProperty(property, defaultValue);
    }

    public static void loadAllProperties(String pathToFolder){
        File folder = new File(pathToFolder);
        for (String filename: Objects.requireNonNull(folder.list((dir, name) -> name.endsWith(ENDING))))
            loadProperties(folder, filename);
    }

    private static void loadProperties(File folder, String fileName){
        Properties loadedProperties = new Properties();
        int dotIndex = fileName.lastIndexOf('.');
        String prefix = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        try {
            loadedProperties.load(new FileInputStream(folder.getPath() + "\\" + fileName));
        } catch (IOException e) {
            Logger.error("Repository can not load " + folder.getPath() + "\\" + fileName);
            return;
        }

        loadedProperties.forEach((k, v) -> knownProperties.put(buildName(prefix, (String) k), v));
    }

    private static String buildName(String... parts){
        return String.join(".", parts);
    }
}
