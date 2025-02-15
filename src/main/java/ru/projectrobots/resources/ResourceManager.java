package ru.projectrobots.resources;

import org.jetbrains.annotations.Nullable;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;

class ResourceManager {

    public static void loadAllProperties(String pathToFolder) {
        PropertiesProvider.loadAllProperties(pathToFolder);
    }

    public static int getFramesCount(String action){
        return Integer.parseInt(PropertiesProvider.getProperty(action + ".count", "1"));
    }

    public static List<String> getLooks(String entity) {
        return List.of(PropertiesProvider.getProperty(entity + ".looks", "").split(","));
    }

    public static List<String> getGameEntities(){
        return List.of(PropertiesProvider.getProperty("game.entities", "").split(","));
    }

    public static List<String> getBackgroundTracks(){
        return List.of(PropertiesProvider.getProperty("sounds.backgrounds", "").split(","));
    }

    public static String getLocalizedPhrase(String phrase, Locale locale){
        String phrase_name = String.format("phrases_%s.%s", locale.getLanguage(), phrase);
        return PropertiesProvider.getProperty(phrase_name, phrase);
    }

    public static Image getImage(String imageName, boolean forOftenUse, boolean reload) throws NoSuchFieldException, FileNotFoundException {
        return ResourceProvider.getImage(imageName, forOftenUse, reload);
    }

    public static Icon getIconWithHeight(String iconName, int height) throws FileNotFoundException, NoSuchFieldException {
        return ResourceProvider.getIconWithHeight(iconName, height);
    }

    public static AudioInputStream getSound(String name) {
        return ResourceProvider.getSound(name);
    }

    @Nullable
    public static Font getFont(String name) {
        return ResourceProvider.getFont(name);
    }

    public static Icon getIcon(String iconName, int height, int width)
            throws FileNotFoundException, NoSuchFieldException {
        return ResourceProvider.getIcon(iconName, height, width);
    }
}
