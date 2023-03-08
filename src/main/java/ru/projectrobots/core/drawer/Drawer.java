package ru.projectrobots.core.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import org.jetbrains.annotations.Nullable;
import ru.projectrobots.di.container.ResourceManager;
import ru.projectrobots.log.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class Drawer {
    protected boolean doNotCache = false;
    private final HashMap<String, Image> cachedImages = new LinkedHashMap<>();

    protected void fillOval(Graphics2D g2d, int centerX, int centerY, int diam1, int diam2) {
        g2d.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    protected void drawOval(Graphics2D g2d, int centerX, int centerY, int diam1, int diam2) {
        g2d.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    @Nullable
    protected Image getImageOrNull(String condition){
        String imagePath = ResourceManager.getIconFor(condition);
        if (imagePath == null) {
            Logger.error("Can not get path for \"" + condition + "\" icon from resource manager");
            return null;
        }

        Image image;
        if (!doNotCache) {
            image = cachedImages.get(imagePath);
            if (image != null) return image;
        }

        try {
            image = ImageIO.read(new File(imagePath));
            if (!doNotCache) cachedImages.put(imagePath, image);
            return image;
        } catch (IOException e) {
            Logger.error("Can not find image at " + imagePath);
            return null;
        }
    }
}
