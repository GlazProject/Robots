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
        drawShadow(g2d, target);

        int size = getSize(target);

        Image image = ResourceProvider.getImage(TARGET, true, false);
        g2d.drawImage(image,
                target.getX() - size, target.getY() - size,
                size * 2, size * 2,
                null);
    }

    private int getSize(Target target){
        if(counterForHit++ == HIT_DELAY){
            hit = !hit;
            counterForHit = 0;
        }
        return hit ? target.getSize() : (int)(target.getSize() * 1.2);
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
