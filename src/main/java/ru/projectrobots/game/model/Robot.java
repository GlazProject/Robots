package ru.projectrobots.game.model;

import ru.projectrobots.core.model.BaseModel;
import ru.projectrobots.core.model.Point;
import ru.projectrobots.log.Logger;

public class Robot extends BaseModel {

    private volatile RobotState robotState;
    private double robotDirection = 0;

    public Robot(double x, double y) {
        MAX_ANGULAR_VELOCITY = 0.1;
        MAX_VELOCITY = 0.5;
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

        double delta = robotDirection - angleToTarget;
        if (Math.abs(delta) > Math.PI)
            delta -= Math.signum(delta) * Math.PI * 2;

        double angularVelocity = (delta / Math.PI) * MAX_ANGULAR_VELOCITY;

        Logger.debug("\nRobot direction: " + Math.toDegrees(robotDirection)
                + "\nAngle to target: " + Math.toDegrees(angleToTarget)
                + "\nDelta: " + Math.toDegrees(delta)
                + "\nAngular velocity: " + angularVelocity);

        moveRobot(angularVelocity);
    }

    private Point getNewPosition(double velocity, double angularVelocity){
        double r = velocity * 10;
        double angle = asNormalizedRadians(robotDirection + angularVelocity * 10);

        return new Point(
                x + r * Math.cos(angle),
                y + r * Math.sin(angle)
        );
    }

    private void moveRobot(double angularVelocity) {
        double velocity = (1 - angularVelocity/MAX_ANGULAR_VELOCITY)
                * applyLimits(MAX_VELOCITY, 0, MAX_VELOCITY);

        Point newPosition = getNewPosition(velocity, -angularVelocity);
        Logger.debug(newPosition.toString() + angularVelocity);

        if (isPointInsideBoard(newPosition.x(), newPosition.y())) {
            robotDirection = asNormalizedRadians(robotDirection - angularVelocity * 10d);
            setRobotPosition(newPosition.x(), newPosition.y());
            return;
        }

        robotDirection = getAngleAfterWall(newPosition);
        newPosition = getNewPosition(velocity, -angularVelocity);
        setRobotPosition(newPosition.x(), newPosition.y());
    }

    private double getAngleAfterWall(Point position){
        double wallAngle = (position.x() > boardWidth - border || position.x() < border)
                ? Math.PI
                : Math.PI / 2;
        return asNormalizedRadians(wallAngle * 2 - Math.PI - robotDirection);
    }
}