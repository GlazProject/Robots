package ru.projectrobots.core.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import java.awt.*;

public abstract class Drawer {

    protected void fillOval(Graphics2D g2d, int centerX, int centerY, int diam1, int diam2) {
        g2d.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    protected void drawOval(Graphics2D g2d, int centerX, int centerY, int diam1, int diam2) {
        g2d.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

}
