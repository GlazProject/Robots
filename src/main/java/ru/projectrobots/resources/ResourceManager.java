package ru.projectrobots.resources;

import org.jetbrains.annotations.Nullable;
import ru.projectrobots.log.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ResourceManager {
    private static final String ICONS_PATH = "src/main/resources/icons.properties";
    private static Properties icons = null;

    @Nullable
    public static String getImagePath(String target){
        if (icons == null && propertiesIsNotLoaded()) {
            Logger.error("Can not find property \"" + target + "\" from getImagePath");
            return null;
        }
        return icons.getProperty(target, null);
    }

    public static int getFramesCount(String action){
        if (icons == null && propertiesIsNotLoaded()){
            Logger.error("Can not find property \"" + action + "\" from getFramesCount");
            return 1;
        }
        return Integer.parseInt(icons.getProperty(action + ".count", "1"));
    }

    public static List<String> getLooks(String entity) {
        if (icons == null && propertiesIsNotLoaded()) {
            Logger.error("Can not find property \"" + entity + "\" from getLooks");
            return List.of();
        }
        return List.of(icons.getProperty(entity + ".looks", "").split(","));
    }

    public static List<String> getGameEntities(){
        if (icons == null && propertiesIsNotLoaded()) {
            Logger.error("Can not find property \"game.entities\" from getGameEntities");
            return List.of();
        }
        return List.of(icons.getProperty("game.entities", "").split(","));
    }

    private static boolean propertiesIsNotLoaded(){
        icons = new Properties();
        try {
            icons.load(new FileInputStream(ICONS_PATH));
            return false;
        } catch (IOException e) {
            Logger.error("Properties manager can not load " + ICONS_PATH);
            icons = null;
            return true;
        }
    }
}
