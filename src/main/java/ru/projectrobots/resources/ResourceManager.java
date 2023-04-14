package ru.projectrobots.resources;

import java.util.List;

class ResourceManager {
    public static String getImagePath(String target){
        return PropertiesManager.getProperty(target, null);
    }

    public static int getFramesCount(String action){
        return Integer.parseInt(PropertiesManager.getProperty(action + ".count", "1"));
    }

    public static List<String> getLooks(String entity) {
        return List.of(PropertiesManager.getProperty(entity + ".looks", "").split(","));
    }

    public static List<String> getGameEntities(){
        return List.of(PropertiesManager.getProperty("game.entities", "").split(","));
    }
}
