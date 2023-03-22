package ru.projectrobots.resources;

import org.jetbrains.annotations.Nullable;
import ru.projectrobots.log.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ResourceProvider {
    private static final HashMap<String, Image> cachedImages = new LinkedHashMap<>();
    private static final String DEFAULT_IMAGE = "default.image";

    public static Image getImage(String imageName, boolean forOftenUse, boolean reload) throws NoSuchFieldException, FileNotFoundException {
        String imagePath = ResourceManager.getImagePath(imageName);

        if (imagePath == null) {
            Logger.error("Can not get path for \"" + imageName + "\" icon from resource manager");
            if (imageName.equals(DEFAULT_IMAGE))
                throw new NoSuchFieldException();
            return getImage(DEFAULT_IMAGE, true, false);
        }

        Image image = loadImage(imagePath, forOftenUse, reload);
        if (image != null) return image;
        if (imageName.equals(DEFAULT_IMAGE))
            throw new FileNotFoundException(imagePath);
        return getImage(DEFAULT_IMAGE, true, false);
    }

    @Nullable
    private static Image loadImage(String imagePath, boolean needCaching, boolean reload){
        Image image;
        if (!reload){
            image = cachedImages.get(imagePath);
            if (image != null) return image;
        }

        try {
            image = ImageIO.read(new File(imagePath));
            if (needCaching) cachedImages.put(imagePath, image);
            return image;
        } catch (IOException e) {
            Logger.error("Can not find image at " + imagePath);
            return null;
        }
    }
}
