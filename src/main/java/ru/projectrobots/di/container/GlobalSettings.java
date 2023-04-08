package ru.projectrobots.di.container;

import ru.projectrobots.resources.ResourceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalSettings {
    private final static Map<String, String> spritesNames = new HashMap<>();

    public static void setSprite(String objectId, String name){
        spritesNames.put(objectId, name);
    }

    public static void setDefaultSprites(List<String> entities){
        for (String entity : entities) {
            spritesNames.put(entity, ResourceManager.getLooks(entity).get(0));
        }
    }

    public static String getSpriteName(String objectId){
        String sprite = spritesNames.get(objectId);
        return sprite == null
                ? ""
                : sprite;
    }
}
