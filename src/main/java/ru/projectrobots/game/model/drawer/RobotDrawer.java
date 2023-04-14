package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.Models;
import ru.projectrobots.resources.Repository;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.RobotState;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Objects;

public class RobotDrawer extends Drawer {
    private static class Direction {
        public static final String LEFT = "left";
        public static final String RIGHT = "right";
        public static final String UP = "back";
        public static final String DOWN = "front";
    }

    private static final String STAYING = "staying";
    private static final String MOVING = "moving";
    public static final String MODEL_NAME = Models.CHARACTER;

    private int nextFrame = 0;
    private String lastDirection = Direction.UP;
    private String lastAction = STAYING;

    public void drawRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        drawShadow(g2d, robot);
        drawRobot(g2d, robot, (robot.getRobotState() == RobotState.STAYING) ? STAYING : MOVING);
    }

    private void drawShadow(Graphics2D g2d, Robot robot) {
        g2d.setColor(new Color(41, 49, 51, 40));
        g2d.fillOval(robot.getX() - robot.getRobotWidth(),
                robot.getY() + robot.getRobotHeight() / 6,
                (int)(robot.getRobotWidth() * 1.4),
                (int)(robot.getRobotHeight() * 0.4)
                );
    }

    private void drawRobot(Graphics2D g2d, Robot robot, String action) throws FileNotFoundException, NoSuchFieldException {
        String asset = GlobalSettings.getSpriteName(MODEL_NAME);
        String direction = get4AxisDirection(robot.getRobotDirection());

        if (!Objects.equals(direction, lastDirection) || !Objects.equals(action, lastAction)){
            nextFrame = 0;
            lastDirection = direction;
            lastAction = action;
        }

        String entityName = createFullName(MODEL_NAME, asset, direction, action);
        String frameName = createFullName(entityName, String.valueOf(++nextFrame));

        Image image = Repository.getImage(frameName, true, false);
        drawImage(image, robot, g2d);

        int totalFramesCount = Repository.getFramesCount(entityName);
        nextFrame %= totalFramesCount;
    }

    private String get4AxisDirection(double angle){
        double sector = Math.PI / 8;
        if (angle < sector * 2 || angle > sector * 14) return Direction.RIGHT;
        if (angle < sector * 6) return Direction.DOWN;
        if (angle < sector * 10) return Direction.LEFT;
        return Direction.UP;
    }

    private void drawImage(Image image, Robot robot, Graphics2D g2d){
        g2d.drawImage(image,
                robot.getX() - robot.getRobotWidth() / 2,
                robot.getY() - robot.getRobotHeight() / 2,
                robot.getRobotWidth(),
                robot.getRobotHeight(),
                null);
    }
}
