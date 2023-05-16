package ru.projectrobots.di.container;

import ru.projectrobots.resources.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GlobalSettings {
    private final static Map<String, String> spritesNames = new HashMap<>();
    private static Locale mainLocale = new Locale("ru");

    public static void setSprite(String objectId, String name){
        spritesNames.put(objectId, name);
    }

    public static void setLocale(Locale locale){
        mainLocale = locale;
    }

    public static Locale getLocale(){
        return mainLocale;
    }

    public static void setDefaultSprites(List<String> entities){
        for (String entity : entities) {
            spritesNames.put(entity, Repository.getLooks(entity).get(0));
        }
    }

    public static String getSpriteName(String objectId){
        String sprite = spritesNames.get(objectId);
        return sprite == null
                ? ""
                : sprite;
    }
}
