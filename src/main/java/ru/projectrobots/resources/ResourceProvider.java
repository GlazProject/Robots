package ru.projectrobots.resources;

import org.jetbrains.annotations.Nullable;
import ru.projectrobots.log.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

class ResourceProvider {
    private static final HashMap<String, Image> cachedImages = new LinkedHashMap<>();
    private static final String DEFAULT_IMAGE = "game.default.image";

    private static final String DEFAULT_BACKGROUND = "sounds.background1";

    public static Image getImage(String imageName, boolean forOftenUse, boolean reload) throws NoSuchFieldException, FileNotFoundException {
        String imagePath = PropertiesProvider.getProperty(imageName, null);

        if (imagePath == null) {
            Logger.error("Can not get path for \"" + imageName + "\" icon");
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

    public static Icon getIconWithHeight(String iconName, int height) throws FileNotFoundException, NoSuchFieldException {
        Image image = getImage(iconName, true, false);
        double scale = 1.0 * height / image.getHeight(null);
        int newWidth = (int)(image.getWidth(null) * scale);
        Image scaledImage = image.getScaledInstance(newWidth, height, Image.SCALE_FAST);
        return new ImageIcon(scaledImage);
    }

    public static AudioInputStream getSound(String soundName) {
        String path = PropertiesProvider.getProperty(soundName, null);

        if (path == null) {
            Logger.error("Can not get path for \"" + soundName + "\" sound");
            return loadSound(DEFAULT_BACKGROUND);
        }

        AudioInputStream audio = loadSound(path);
        if (audio != null) return audio;

        return loadSound(DEFAULT_BACKGROUND);
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

    @Nullable
    private static AudioInputStream loadSound(String soundPath) {
        AudioInputStream audio;

        try {
            audio = AudioSystem.getAudioInputStream(new File(soundPath));
            return audio;
        } catch (UnsupportedAudioFileException | IOException e) {
            Logger.error("Couldn't find sound at " + soundPath);
            return null;
        }
    }
}
