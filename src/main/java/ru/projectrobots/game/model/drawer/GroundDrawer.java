package ru.projectrobots.game.model.drawer;

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.Models;
import ru.projectrobots.resources.ResourceProvider;
import ru.projectrobots.game.view.GameView;

import java.awt.*;
import java.io.FileNotFoundException;

public class GroundDrawer extends Drawer {
    public void drawGround(Graphics2D g2d, GameView frame) throws FileNotFoundException, NoSuchFieldException {
        String asset = GlobalSettings.getSpriteName(Models.GROUND);
        Image image = ResourceProvider.getImage(Models.GROUND + "." + asset, true, false);

        int imageWidth = 250;
        int imageHeight = 250;
        image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_FAST);

        int horizontalCount = (int)Math.ceil(1d * frame.getWidth() / imageWidth);
        int verticalCount = (int)Math.ceil(1d * frame.getHeight() / imageHeight);

        for(int i = 0; i< horizontalCount;i++)
            for (int j = 0;j<verticalCount;j++){
                g2d.drawImage(image, i*imageWidth, j*imageHeight, frame);
            }
    }
}
