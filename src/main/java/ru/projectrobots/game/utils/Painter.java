package ru.projectrobots.game.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Painter {

    private static Graphics2D g2d;

    private final static int ROBOT_HEIGHT = 50;
    private final static int ROBOT_WIDTH = 70;
    private final static int BORDER = 5;

    private final static int TARGET_SIZE = 20;

    public Painter(Graphics2D _g2d) {
        g2d = _g2d;
    }

    private static void fillOval(int centerX, int centerY, int diam1, int diam2) {
        g2d.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(int centerX, int centerY, int diam1, int diam2) {
        g2d.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    public void drawRobot(int x, int y, double direction) {
        AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
        g2d.setTransform(t);
        g2d.setColor(Color.RED);
        fillOval(x, y, ROBOT_HEIGHT, ROBOT_WIDTH);
        g2d.setColor(Color.BLACK);
        drawOval(x, y, ROBOT_HEIGHT, ROBOT_WIDTH);
        g2d.setColor(Color.WHITE);
        fillOval(x + BORDER*2, y, BORDER, BORDER);
        g2d.setColor(Color.BLACK);
        drawOval(x + BORDER*2, y, BORDER, BORDER);
    }

    public void drawTarget(int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g2d.setTransform(t);
        g2d.setColor(Color.GREEN);
        fillOval(x, y, TARGET_SIZE, TARGET_SIZE);
        g2d.setColor(Color.BLACK);
        drawOval(x, y, TARGET_SIZE, TARGET_SIZE);
    }
}
