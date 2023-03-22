package ru.projectrobots.di.container;

import org.jetbrains.annotations.Nullable;
import ru.projectrobots.log.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResourceManager {
    private static final String ICONS_PATH = "src/main/resources/icons.properties";

    private static Properties icons = null;

    @Nullable
    public static String getIconFor(String target){
        if (icons == null && !loadIcons()) return null;
        return icons.getProperty(target, null);
    }

    public static int getFramesCount(String action){
        if (icons == null && !loadIcons()) return 0;
        return Integer.parseInt(icons.getProperty(action + ".count", "0"));
    }

    private static boolean loadIcons(){
        icons = new Properties();
        try {
            icons.load(new FileInputStream(ICONS_PATH));
            return true;
        } catch (IOException e) {
            Logger.error("Properties manager can not load " + ICONS_PATH);
            icons = null;
            return false;
        }
    }
}
