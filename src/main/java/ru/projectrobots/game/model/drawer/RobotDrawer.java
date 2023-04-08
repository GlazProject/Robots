package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.Models;
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
        Up,
        Down
    }

    private static final String FRONT_STAYING = "front.staying";
    private static final String FRONT_MOVING = "front.moving";
    private static final String BACK_STAYING = "back.staying";
    private static final String BACK_MOVING = "back.moving";
    private static final String LEFT_STAYING = "left.staying";
    private static final String LEFT_MOVING = "left.moving";
    private static final String RIGHT_STAYING = "right.staying";
    private static final String RIGHT_MOVING = "right.moving";

    private int nextMovingFrame = 0;
    private Direction lastMovingDirection = Direction.Up;

    public void drawRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        drawShadow(g2d, robot);
        if (robot.getRobotState() == RobotState.STAYING)
            drawStayingRobot(g2d, robot);
        else
            drawMovingRobot(g2d, robot);
    }

    private void drawShadow(Graphics2D g2d, Robot robot) {
        g2d.setColor(new Color(41, 49, 51, 40));
        g2d.fillOval(robot.getX() - robot.getRobotWidth(),
                robot.getY() + robot.getRobotHeight() / 6,
                (int)(robot.getRobotWidth() * 1.4),
                (int)(robot.getRobotHeight() * 0.4)
                );
    }

    private void drawStayingRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        String asset = GlobalSettings.getSpriteName(Models.character.name());
        Image image = ResourceProvider.getImage(Models.character.name() + "." + asset + "." +
                switch (get4AxisDirection(robot.getRobotDirection())) {
                    case Left -> LEFT_STAYING;
                    case Right -> RIGHT_STAYING;
                    case Up -> BACK_STAYING;
                    case Down -> FRONT_STAYING;
                }, true, false);

        drawImage(image, robot, g2d);
    }

    private void drawMovingRobot(Graphics2D g2d, Robot robot) throws FileNotFoundException, NoSuchFieldException {
        Direction direction = get4AxisDirection(robot.getRobotDirection());
        String asset = GlobalSettings.getSpriteName(Models.character.name());
        String action = switch (direction){
            case Left -> LEFT_MOVING;
            case Right -> RIGHT_MOVING;
            case Up -> BACK_MOVING;
            case Down -> FRONT_MOVING;
        };

        if (direction != lastMovingDirection){
            nextMovingFrame = 0;
            lastMovingDirection = direction;
        }

        Image image = ResourceProvider.getImage(
                Models.character.name() + "." + asset + "." + action + "." + ++nextMovingFrame,
                true,
                false);
        drawImage(image, robot, g2d);

        int totalFramesCount = ResourceManager.getFramesCount(Models.character.name() + "." + asset + "." + action);
        nextMovingFrame %= totalFramesCount;
    }

    private Direction get4AxisDirection(double angle){
        double sector = Math.PI / 8;
        if (angle < sector * 2 || angle > sector * 14) return Direction.Right;
        if (angle < sector * 6) return Direction.Down;
        if (angle < sector * 10) return Direction.Left;
        return Direction.Up;
    }

    private void drawImage(Image image, Robot robot, Graphics2D g2d){
        g2d.drawImage(image,
                robot.getX() - robot.getRobotWidth() / 2, robot.getY() - robot.getRobotHeight() / 2,
                robot.getRobotWidth(), robot.getRobotHeight(),
                null);
    }
}
