package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.Models;
import ru.projectrobots.resources.Repository;
import ru.projectrobots.game.model.Target;

import java.awt.*;
import java.io.FileNotFoundException;

public class TargetDrawer extends Drawer  {
    private static final int DELAY = 2;
    public static final String MODEL_NAME = Models.TARGET;

    private int nextFrame = 1;
    private int framesCount = 0;

    public void drawTarget(Graphics2D g2d, Target target) throws FileNotFoundException, NoSuchFieldException {
        if (target.isCollected()) return;

        drawShadow(g2d, target);
        int size = target.getSize();

        String asset = GlobalSettings.getSpriteName(MODEL_NAME);
        String entityName = createFullName(MODEL_NAME, asset);
        String frameName = createFullName(entityName, String.valueOf(nextFrame));

        Image image = Repository.getImage(frameName, true, false);

        framesCount++;
        if (framesCount >= DELAY) {
            int totalFramesCount = Repository.getFramesCount(entityName);
            nextFrame %= totalFramesCount;
            nextFrame++;
            framesCount = 0;
        }

        g2d.drawImage(image,
                target.getX() - size, target.getY() - size,
                size * 2, size * 2,
                null);
    }

    private void drawShadow(Graphics2D g2d, Target target) {
        int size = target.getSize();

        g2d.setColor(new Color(41, 49, 51, 70));
        g2d.fillOval(target.getX() - size,
                target.getY() + size,
                (int)(size * 1.4),
                (int)(size * 0.4)
        );
    }
}
