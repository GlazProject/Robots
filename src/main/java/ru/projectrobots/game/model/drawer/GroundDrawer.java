package ru.projectrobots.game.model.drawer;

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.game.view.GameView;

import java.awt.*;

public class GroundDrawer extends Drawer {
    public static final String GROUND = "ground.main";

    public GroundDrawer(){
        doNotCache = true;
    }

    public void drawGround(Graphics2D g2d, GameView frame){
        Image image = getImageOrNull(GROUND);
        if (image == null) return;


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
