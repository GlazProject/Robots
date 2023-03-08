package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.game.model.Target;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TargetDrawer extends Drawer  {

    public void drawTarget(Graphics2D g2d, Target target) {
        int x = target.getX();
        int y = target.getY();
        int size = target.getSize();

        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g2d.setTransform(t);
        g2d.setColor(Color.GREEN);
        fillOval(g2d, x, y, size, size);
        g2d.setColor(Color.BLACK);
        drawOval(g2d, x, y, size, size);
    }
}
