package ru.projectrobots.game.model.drawer;

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.resources.ResourceProvider;
import ru.projectrobots.game.view.GameView;

import java.awt.*;
import java.io.FileNotFoundException;

public class GroundDrawer extends Drawer {
    public static final String GROUND = "ground.main";

    public void drawGround(Graphics2D g2d, GameView frame) throws FileNotFoundException, NoSuchFieldException {
        Image image = ResourceProvider.getImage(GROUND, true, false);

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        int horizontalCount = (int)Math.ceil(1d * frame.getWidth() / imageWidth);
        int verticalCount = (int)Math.ceil(1d * frame.getHeight() / imageHeight);

        for(int i = 0; i< horizontalCount;i++)
            for (int j = 0;j<verticalCount;j++){
                g2d.drawImage(image, i*imageWidth, j*imageHeight, frame);
            }
    }
}
