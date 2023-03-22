package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.resources.ResourceManager;
import ru.projectrobots.resources.ResourceProvider;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.RobotState;

import java.awt.*;
import java.io.FileNotFoundException;

public class RobotDrawer extends Drawer {
    private enum Direction {
        Left,
        Right,
        Front,
        Back
    }

    private static final String FRONT_STAYING = "robot.front.staying";
    private static final String FRONT_MOVING = "robot.front.moving";
    private static final String BACK_STAYING = "robot.back.staying";
    private static final String BACK_MOVING = "robot.back.moving";
    private static final String LEFT_STAYING = "robot.left.staying";
    private static final String LEFT_MOVING = "robot.left.moving";
    private static final String RIGHT_STAYING = "robot.right.staying";
    private static final String RIGHT_MOVING = "robot.right.moving";

    private int nextMovingFrame = 0;
    private Direction lastMovingDirection = Direction.Front;

    public void drawRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        if (robot.getRobotState() == RobotState.STAYING)
            drawStayingRobot(g2d, robot);
        else
            drawMovingRobot(g2d, robot);
    }

    private void drawStayingRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        Image image = ResourceProvider.getImage(
                switch (get4AxisDirection(robot.getRobotDirection())) {
                    case Left -> LEFT_STAYING;
                    case Right -> RIGHT_STAYING;
                    case Front -> FRONT_STAYING;
                    case Back -> BACK_STAYING;
                }, true, false);

        drawImage(image, robot, g2d);
    }

    private void drawMovingRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        Direction direction = get4AxisDirection(robot.getRobotDirection());
        String action = switch (direction){
            case Left -> LEFT_MOVING;
            case Right -> RIGHT_MOVING;
            case Front -> FRONT_MOVING;
            case Back -> BACK_MOVING;
        };

        if (direction != lastMovingDirection){
            nextMovingFrame = 0;
            lastMovingDirection = direction;
        }

        Image image = ResourceProvider.getImage(action + "." + ++nextMovingFrame, true, false);
        drawImage(image, robot, g2d);

        int totalFramesCount = ResourceManager.getFramesCount(action);
        nextMovingFrame %= totalFramesCount;
    }

    private Direction get4AxisDirection(double angle){
        double sector = Math.PI / 8;
        if (angle < sector || angle > sector * 13) return Direction.Right;
        if (angle < sector * 7) return Direction.Front;
        if (angle < sector * 11) return Direction.Left;
        return Direction.Back;
    }

    private void drawImage(Image image, Robot robot, Graphics2D g2d){
        g2d.drawImage(image,
                robot.getX() - robot.getRobotWidth() / 2, robot.getY() - robot.getRobotHeight() / 2,
                robot.getRobotWidth(), robot.getRobotHeight(),
                null);
    }
}
