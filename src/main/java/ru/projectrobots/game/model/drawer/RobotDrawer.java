package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.game.model.Robot;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotDrawer extends Drawer {

    public void drawRobot(Graphics2D g2d, Robot robot) {
        int x = robot.getX();
        int y = robot.getY();
        int border = robot.getBorder();

        AffineTransform t = AffineTransform.getRotateInstance(robot.getRobotDirection(), x, y);

        g2d.setTransform(t);
        g2d.setColor(Color.RED);
        fillOval(g2d, x, y, robot.getRobotHeight(), robot.getRobotWidth());
        g2d.setColor(Color.BLACK);
        drawOval(g2d, x, y, robot.getRobotHeight(), robot.getRobotWidth());
        g2d.setColor(Color.WHITE);
        fillOval(g2d, x + border*2, y, border, border);
        g2d.setColor(Color.BLACK);
        drawOval(g2d, x + border*2, y, border, border);
    }
}
