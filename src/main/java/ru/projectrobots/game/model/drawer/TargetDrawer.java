package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.game.model.Target;

import java.awt.*;

public class TargetDrawer extends Drawer  {
    public static final String TARGET = "target";
    private static final int HIT_DELAY = 4;

    private boolean hit = false;
    private int counterForHit = 0;

    public void drawTarget(Graphics2D g2d, Target target) {
        if (target.isCollected()) return;

        int x = target.getX();
        int y = target.getY();
        int size = target.getSize();

        if (hit) size *= 1.2;
        if(counterForHit++ == HIT_DELAY){
            hit = !hit;
            counterForHit = 0;
        }

//        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
//        t = AffineTransform.getTranslateInstance(10,10);
//        AffineTransform oldTransform = g2d.getTransform();
//        g2d.setTransform(t);

        Image image = getImageOrNull(TARGET);
        if (image == null) drawDefaultTarget(g2d, target);
        else g2d.drawImage(image,
                x - size, y - size,
                size * 2, size * 2,
                null);

//        g2d.setTransform(oldTransform);
    }

    private void drawDefaultTarget(Graphics2D g2d, Target target){
        int x = target.getX();
        int y = target.getY();
        int size = target.getSize();

        g2d.setColor(Color.GREEN);
        fillOval(g2d, x, y, size, size);
        g2d.setColor(Color.BLACK);
        drawOval(g2d, x, y, size, size);
    }
}
