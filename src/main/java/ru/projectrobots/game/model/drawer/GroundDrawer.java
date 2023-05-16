package ru.projectrobots.game.model.drawer;

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.Models;
import ru.projectrobots.resources.Repository;
import ru.projectrobots.game.view.GameFrame;

import java.awt.*;
import java.io.FileNotFoundException;

public class GroundDrawer extends Drawer {
    public static final String MODEL_NAME = Models.GROUND;

    public void drawGround(Graphics2D g2d, GameFrame frame) throws FileNotFoundException, NoSuchFieldException {
        int imageWidth = 250;
        int imageHeight = 250;

        String asset = GlobalSettings.getSpriteName(MODEL_NAME);
        String entityName = createFullName(MODEL_NAME, asset);
        Image image = Repository
                .getImage(entityName, true, false)
                .getScaledInstance(imageWidth, imageHeight, Image.SCALE_FAST);

        int horizontalNumber = (int)Math.ceil(1d * frame.getWidth() / imageWidth);
        int verticalNumber = (int)Math.ceil(1d * frame.getHeight() / imageHeight);

        for(int i = 0; i< horizontalNumber;i++)
            for (int j = 0;j<verticalNumber;j++){
                g2d.drawImage(image, i*imageWidth, j*imageHeight, frame);
            }
    }
}
