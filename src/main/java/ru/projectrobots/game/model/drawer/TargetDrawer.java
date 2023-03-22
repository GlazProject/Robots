package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.resources.ResourceProvider;
import ru.projectrobots.game.model.Target;

import java.awt.*;
import java.io.FileNotFoundException;

public class TargetDrawer extends Drawer  {
    public static final String TARGET = "target";
    private static final int HIT_DELAY = 4;

    private boolean hit = false;
    private int counterForHit = 0;

    public void drawTarget(Graphics2D g2d, Target target) throws FileNotFoundException, NoSuchFieldException {
        if (target.isCollected()) return;
        int size = target.getSize();

        if (hit) size *= 1.2;
        if(counterForHit++ == HIT_DELAY){
            hit = !hit;
            counterForHit = 0;
        }

        Image image = ResourceProvider.getImage(TARGET, true, false);
        g2d.drawImage(image,
                target.getX() - size, target.getY() - size,
                size * 2, size * 2,
                null);
    }
}
