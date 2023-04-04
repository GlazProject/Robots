package ru.projectrobots.game.model;

/* created by zzemlyanaya on 24/03/2023 */

import ru.projectrobots.core.model.BaseModel;

import java.util.Random;
import java.util.UUID;

public class Fireball extends BaseModel {

    private final static Random rnd = new Random();

    private String id;

    private final double speed = 5;
    private double velocity = 0.2;

    private boolean isLTR = true;
    private boolean isFinished = false;

    public Fireball(double x, double y) {
        this.x = x;
        this.y = y;

        modelWidth = 128;
        modelHeight = 64;
    }

    public double getSpeed() {
        return speed;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isLTR() {
        return isLTR;
    }

    public void setLTR(boolean LTR) {
        isLTR = LTR;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getId() {
        return id;
    }

    public void setPosition(double x, double y) {
        if (!isPointInsideBoard(x, y)) {
            isFinished = true;
            return;
        }

        this.x = x;
        this.y = y;
        isFinished = false;
    }

    public static Fireball generateFireball(int boardWidth, int boardHeight) {
        Fireball fireball = new Fireball(0, 0);
        fireball.setBoardSize(boardWidth, boardHeight);

        double x = fireball.getModelWidth() / 2.0;
        double y = boardHeight * rnd.nextDouble();
        double v = 0.9*rnd.nextDouble()+0.2;
        fireball.setVelocity(v);

        if (rnd.nextBoolean()) {
            fireball.setLTR(true);
            fireball.setPosition(x, y);
        } else {
            fireball.setLTR(false);
            fireball.setPosition(boardWidth-x, y);
        }

        fireball.id = UUID.randomUUID().toString();

        return fireball;
    }

    public void update(Target target, Robot robot) {
        if (distance(target.getX(), target.getY(), x, y) < target.getSize() / 2d) {
            target.setCollected();
        }

        boolean robotAffected = false;
        if (distance(robot.getX(), robot.getY(), x, y) < robot.getRobotHeight() / 2d) {
            robot.setRobotState(RobotState.STAYING);
            robotAffected = true;
        }

        double move = speed * velocity * (isLTR ? 1 : -1);
        setPosition(x + move, y);
        if (robotAffected) robot.setRobotPosition(robot.getX()+move, robot.getY());
    }
}
