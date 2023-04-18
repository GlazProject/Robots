package ru.projectrobots.resources;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.List;

public class Repository {
    public synchronized static void loadAllProperties(String pathToFolder) {
        PropertiesManager.loadAllProperties(pathToFolder);
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

    public synchronized static Image getImage(String imageName, boolean forOftenUse, boolean reload)
            throws FileNotFoundException, NoSuchFieldException {
        return ResourceProvider.getImage(imageName, forOftenUse, reload);
    }

    public synchronized static Icon getIconWithHeight(String iconName, int height)
            throws FileNotFoundException, NoSuchFieldException {
        return ResourceProvider.getIconWithHeight(iconName, height);
    }
}
