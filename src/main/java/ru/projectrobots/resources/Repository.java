package ru.projectrobots.resources;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;

public class Repository {

    public synchronized static void loadAllProperties(String pathToFolder) {
        ResourceManager.loadAllProperties(pathToFolder);
    }

    public synchronized static int getFramesCount(String action){
        return ResourceManager.getFramesCount(action);
    }

    public synchronized static List<String> getLooks(String entity) {
        return ResourceManager.getLooks(entity);
    }

    public synchronized static List<String> getGameEntities(){
        return ResourceManager.getGameEntities();
    }

    public synchronized static List<String> getBackgroundTracks(){
        return ResourceManager.getBackgroundTracks();
    }

    public synchronized static Image getImage(String imageName, boolean forOftenUse, boolean reload)
            throws FileNotFoundException, NoSuchFieldException {
        return ResourceManager.getImage(imageName, forOftenUse, reload);
    }

    public synchronized static Icon getIconWithHeight(String iconName, int height)
            throws FileNotFoundException, NoSuchFieldException {
        return ResourceManager.getIconWithHeight(iconName, height);
    }

    public synchronized static String getLocalePhrase(String phrase, Locale locale){
        return ResourceManager.getLocalizedPhrase(phrase, locale);
    }

    public synchronized static AudioInputStream getSound(String name) {
        return ResourceManager.getSound(name);
    }
}
