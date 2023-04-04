package ru.projectrobots.game.model;

import ru.projectrobots.core.model.BaseModel;

public class Robot extends BaseModel {

    private volatile RobotState robotState;
    private double robotDirection = 0;

    public Robot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getRobotHeight() {
        return modelHeight;
    }

    public Robot setRobotHeight(int robotHeight) {
        this.modelHeight = robotHeight;
        return this;
    }

    public int getRobotWidth() {
        return modelWidth;
    }

    public Robot setRobotWidth(int robotWidth) {
        this.modelWidth = robotWidth;
        return this;
    }

    public double getRobotDirection() {
        return this.robotDirection;
    }

    public void setRobotPosition(double x, double y) {
        if (!isPointInsideBoard(x, y)) return;

        this.x = x;
        this.y = y;
    }

    public RobotState getRobotState(){
        return robotState;
    }

    public void setRobotState(RobotState robotState) {
        this.robotState = robotState;
    }

    public void update(Target target) {
        if (target.isCollected()
            || distance(target.getX(), target.getY(), x, y) < target.getSize() / 2d) {
            target.setCollected();
            robotState = RobotState.STAYING;
            return;
        }

        robotState = RobotState.MOVING;
        double angleToTarget = angleTo(
            x,
            y,
            target.getX(),
            target.getY()
        );

        double angularVelocity = 0;
        if (angleToTarget > robotDirection) angularVelocity = MAX_ANGULAR_VELOCITY;
        if (angleToTarget < robotDirection) angularVelocity = -MAX_ANGULAR_VELOCITY;

        moveRobot(angularVelocity);
    }

    private double getNewX(double velocity, double angularVelocity) {
        double newX = x + velocity / angularVelocity *
            (Math.sin(robotDirection + angularVelocity * 10) - Math.sin(robotDirection));

        if (!Double.isFinite(newX)) newX = x + velocity * 10 * Math.cos(robotDirection);

        return newX;
    }

    private double getNewY(double velocity, double angularVelocity) {
        double newY = y - velocity / angularVelocity *
            (Math.cos(robotDirection + angularVelocity * 10) - Math.cos(robotDirection));

        if (!Double.isFinite(newY)) newY = y + velocity * 10 * Math.sin(robotDirection);

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
}