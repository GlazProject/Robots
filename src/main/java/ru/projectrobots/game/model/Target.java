package ru.projectrobots.game.model;

/* created by zzemlyanaya on 07/03/2023 */

import java.awt.*;

public class Target {

    private int x;
    private int y;

    private int size = 20;

    private volatile double boardWidth = 100;
    private volatile double boardHeight = 100;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTargetPosition(Point point) {
        if (!isPointInsideBoard(point)) return;

        x = point.x;
        y = point.y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBoardSize(double width, double height) {
        this.boardWidth = width - 10;
        this.boardHeight = height - 5;
    }

    private boolean isPointInsideBoard(Point point) {
        return point.x > 0 && point.x < boardWidth && point.y > 0 && point.y < boardHeight;
    }
}
