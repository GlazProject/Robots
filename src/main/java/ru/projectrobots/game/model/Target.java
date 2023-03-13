package ru.projectrobots.game.model;

/* created by zzemlyanaya on 07/03/2023 */

import java.awt.*;

public class Target {

    private int x;
    private int y;

    private int size = 20;

    private volatile int boardWidth = 100;
    private volatile int boardHeight = 100;
    private volatile boolean collected = false;

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

    public boolean isCollected(){
        return collected;
    }

    public void setCollected(){
        collected = true;
    }

    public void setTargetPosition(Point point) {
        if (!isPointInsideBoard(point)) return;

        x = point.x;
        y = point.y;
        collected = false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBoardSize(int width, int height) {
        this.boardWidth = width - 10;
        this.boardHeight = height - 5;

        putTargetInBord();
    }

    private boolean isPointInsideBoard(Point point) {
        return isPointInsideBoard(point.x, point.y);
    }

    private boolean isPointInsideBoard(int x, int y) {
        return x > 0 && x < boardWidth && y > 0 && y < boardHeight;
    }

    private void putTargetInBord(){
        if (isPointInsideBoard(x, y)) return;
        if (x > boardWidth - 5) x = boardWidth - 5;
        if (y > boardHeight - 5) y = boardHeight - 5;
    }
}
