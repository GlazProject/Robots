package ru.projectrobots.game.model;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.model.BaseModel;

import java.awt.*;

public class Target extends BaseModel {
    private int size = 20;

    private volatile boolean collected = false;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
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

    private boolean isPointInsideBoard(Point point) {
        return isPointInsideBoard(point.x, point.y);
    }
}
