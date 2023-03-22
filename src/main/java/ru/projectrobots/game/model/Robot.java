package ru.projectrobots.game.model;

public class Robot {
    private int robotHeight = 50;
    private int robotWidth = 70;
    private int border = 5;

    private volatile double x;
    private volatile double y;
    private volatile RobotState robotState;
    private double robotDirection = 0;

    private volatile int boardWidth = 100;
    private volatile int boardHeight = 100;

    private static final double MAX_VELOCITY = 0.1;
    private static final double MAX_ANGULAR_VELOCITY = 0.001;

    public Robot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getRobotHeight() {
        return robotHeight;
    }

    public Robot setRobotHeight(int robotHeight) {
        this.robotHeight = robotHeight;
        return this;
    }

    public int getRobotWidth() {
        return robotWidth;
    }

    public Robot setRobotWidth(int robotWidth) {
        this.robotWidth = robotWidth;
        return this;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getX() {
        return round(this.x);
    }

    public int getY() {
        return round(this.y);
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

    public void update(Target target) {
        if (distance(
            target.getX(), target.getY(),
            x, y) < target.getSize() / 2d){
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

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) angle += 2 * Math.PI;
        while (angle >= 2 * Math.PI) angle -= 2 * Math.PI;

        return angle;
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    public void setBoardSize(int width, int height) {
        this.boardWidth = width - 10;
        this.boardHeight = height - 5;

        putRobotInBord();
    }

    private boolean isPointInsideBoard(double x, double y) {
        return x > border && x < boardWidth - border && y > border && y < boardHeight - border;
    }

    private void putRobotInBord(){
        if (isPointInsideBoard(x, y)) return;
        if (x > boardWidth - border) x = boardWidth - border;
        if (y > boardHeight - border) y = boardHeight - border;
    }
}