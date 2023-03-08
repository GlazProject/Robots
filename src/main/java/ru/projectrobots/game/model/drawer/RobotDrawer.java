package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import org.jetbrains.annotations.Nullable;
import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.di.container.ResourceManager;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.RobotState;

import java.awt.*;
import java.awt.geom.AffineTransform;

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

    public void drawRobot(Graphics2D g2d, Robot robot) {
//        AffineTransform t = AffineTransform.getRotateInstance(0, robot.getX(), robot.getY());
        AffineTransform oldTransform = g2d.getTransform();
//        g2d.setTransform(t);

        if (robot.getRobotState() == RobotState.STAYING)
            drawStayingRobot(g2d, robot);
        else
            drawMovingRobot(g2d, robot);

        g2d.setTransform(oldTransform);
    }

    private void drawStayingRobot(Graphics2D g2d, Robot robot){
        Image image = getImageOrNull(
                switch (get4AxisDirection(robot.getRobotDirection())) {
                    case Left -> LEFT_STAYING;
                    case Right -> RIGHT_STAYING;
                    case Front -> FRONT_STAYING;
                    case Back -> BACK_STAYING;
                });

        drawRobot(image, g2d, robot);
    }

    private void drawMovingRobot(Graphics2D g2d, Robot robot){
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

        Image image = getImageOrNull(action + "." + ++nextMovingFrame);
        drawRobot(image, g2d, robot);

        int totalFramesCount = ResourceManager.getFramesCount(action);
        nextMovingFrame %= totalFramesCount;
    }

    private void drawRobot(@Nullable Image image, Graphics2D g2d, Robot robot){
        if (image == null) drawDefaultRobot(g2d, robot);
        else g2d.drawImage(image,
                robot.getX() - robot.getRobotWidth() / 2, robot.getY() - robot.getRobotHeight() / 2,
                robot.getRobotWidth(), robot.getRobotHeight(),
                null);
    }

    private Direction get4AxisDirection(double angle){
        double sector = Math.PI / 8;
        if (angle < sector || angle > sector * 13) return Direction.Right;
        if (angle < sector * 7) return Direction.Front;
        if (angle < sector * 11) return Direction.Left;
        return Direction.Back;
    }

    private void drawDefaultRobot(Graphics2D g2d, Robot robot){
        int x = robot.getX();
        int y = robot.getY();
        int border = robot.getBorder();

        AffineTransform t = AffineTransform.getRotateInstance(robot.getRobotDirection(), x, y);
        g2d.setTransform(t);
        g2d.setColor(Color.RED);
        fillOval(g2d, x, y, robot.getRobotHeight(), robot.getRobotWidth());
        g2d.setColor(Color.BLACK);
        drawOval(g2d, x, y, robot.getRobotHeight(), robot.getRobotWidth());
        g2d.setColor(Color.WHITE);
        fillOval(g2d, x + border*2, y, border, border);
        g2d.setColor(Color.BLACK);
        drawOval(g2d, x + border*2, y, border, border);
    }
}
