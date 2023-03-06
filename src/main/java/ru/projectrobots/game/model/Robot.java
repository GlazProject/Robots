package ru.projectrobots.game.model;

import java.awt.*;

public class Robot {

    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private double robotDirection = 0;

    private volatile double boardWidth = 100;
    private volatile double boardHeight = 100;

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;


    public int getRobotPositionX() {
        return round(this.robotPositionX);
    }

    public int getRobotPositionY() {
        return round(this.robotPositionY);
    }

    public double getRobotDirection() {
        return this.robotDirection;
    }

    public int getTargetPositionX() {
        return this.targetPositionX;
    }

    public int getTargetPositionY() {
        return this.targetPositionY;
    }

    public void setTargetPosition(Point point) {
        if (!isPointInsideBoard(point)) return;

        targetPositionX = point.x;
        targetPositionY = point.y;
    }

    public void setRobotPosition(double x, double y) {
        if (!isPointInsideBoard(x, y)) return;

        this.robotPositionX = x;
        this.robotPositionY = y;
    }

    private static double distance(double x1, double y1,
                                   double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY,
                                  double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min) return min;

        return Math.min(value, max);
    }

    private double getNewX(double velocity, double angularVelocity) {
        double newX = robotPositionX + velocity / angularVelocity *
            (Math.sin(robotDirection + angularVelocity * 10) - Math.sin(robotDirection));

        if (!Double.isFinite(newX)) newX = robotPositionX + velocity * 10 * Math.cos(robotDirection);

        return newX;
    }

    private double getNewY(double velocity, double angularVelocity) {
        double newY = robotPositionY - velocity / angularVelocity *
            (Math.cos(robotDirection + angularVelocity * 10) - Math.cos(robotDirection));

        if (!Double.isFinite(newY)) newY = robotPositionY + velocity * 10 * Math.sin(robotDirection);

        return newY;
    }

    private void moveRobot(double angularVelocity) {
        double velocity = applyLimits(MAX_VELOCITY, 0, MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -MAX_ANGULAR_VELOCITY, MAX_ANGULAR_VELOCITY);

        double newX = getNewX(velocity, angularVelocity);
        double newY = getNewY(velocity, angularVelocity);

        if (newX > boardWidth || newY > boardHeight || newX < 10 || newY < 5) {
            double wallAngle = 0;
            if (newX > boardWidth || newX < 5) wallAngle = Math.PI / 2;

            robotDirection = wallAngle * 2 - robotDirection;
            newX = getNewX(velocity, angularVelocity);
            newY = getNewY(velocity, angularVelocity);
        } else {
            robotDirection = asNormalizedRadians(robotDirection + angularVelocity * (double) 10);
        }

        setRobotPosition(newX, newY);
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI) angle -= 2 * Math.PI;

        return angle;
    }

    public void update() {
        if (distance(
            targetPositionX, targetPositionY,
            robotPositionX, robotPositionY) < 0.5)
            return;

        double angleToTarget = angleTo(
            robotPositionX,
            robotPositionY,
            targetPositionX,
            targetPositionY
        );

        double angularVelocity = 0;
        if (angleToTarget > robotDirection) angularVelocity = MAX_ANGULAR_VELOCITY;
        if (angleToTarget < robotDirection) angularVelocity = -MAX_ANGULAR_VELOCITY;

        moveRobot(angularVelocity);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    public void setBoardSize(double width, double height) {
        this.boardWidth = width - 10;
        this.boardHeight = height - 5;
    }

    private boolean isPointInsideBoard(Point point) {
        return point.x > 0 && point.x < boardWidth && point.y > 0 && point.y < boardHeight;
    }

    private boolean isPointInsideBoard(double x, double y) {
        return x > 0 && x < boardWidth && y > 0 && y < boardHeight;
    }
}