package ru.projectrobots.game.view;

import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.utils.Painter;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {

    private final Robot robot;

    public GameView(Robot _robot) {
        robot = _robot;
        setDoubleBuffered(true);
    }

    public void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public void onModelUpdateEvent() {
        robot.update();
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        Painter painter = new Painter(g2d);
        painter.drawRobot(
            round(robot.getRobotPositionX()),
            round(robot.getRobotPositionY()),
            robot.getRobotDirection()
        );
        painter.drawTarget(robot.getTargetPositionX(), robot.getTargetPositionY());
    }

}
