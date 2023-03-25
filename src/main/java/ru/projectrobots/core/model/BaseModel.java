package ru.projectrobots.core.model;

/* created by zzemlyanaya on 24/03/2023 */

public abstract class BaseModel {

    protected int modelHeight = 50;
    protected int modelWidth = 70;

    protected volatile int boardWidth = 100;
    protected volatile int boardHeight = 100;

    protected int border = 5;

    protected volatile double x;
    protected volatile double y;

    protected final double MAX_VELOCITY = 0.1;
    protected final double MAX_ANGULAR_VELOCITY = 0.001;

    public int getX() {
        return round(this.x);
    }

    public int getY() {
        return round(this.y);
    }

    public int getModelHeight() {
        return modelHeight;
    }

    public void setModelHeight(int modelHeight) {
        this.modelHeight = modelHeight;
    }

    public int getModelWidth() {
        return modelWidth;
    }

    public void setModelWidth(int modelWidth) {
        this.modelWidth = modelWidth;
    }

    public void setBoardSize(int width, int height) {
        this.boardWidth = width - border*2;
        this.boardHeight = height - border;
        putModelInsideBoard();
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    protected int round(double value) {
        return (int) (value + 0.5);
    }

    protected boolean isPointInsideBoard(double x, double y) {
        return x > border && x < boardWidth - border && y > border && y < boardHeight - border;
    }

    protected void putModelInsideBoard(){
        if (isPointInsideBoard(x, y)) return;
        if (x > boardWidth - border) x = boardWidth - border;
        if (y > boardHeight - border) y = boardHeight - border;
    }

    protected double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    protected double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    protected double asNormalizedRadians(double angle) {
        while (angle < 0) angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI) angle -= 2 * Math.PI;

        return angle;
    }

    protected double applyLimits(double value, double min, double max) {
        if (value < min) return min;

        return Math.min(value, max);
    }
}
