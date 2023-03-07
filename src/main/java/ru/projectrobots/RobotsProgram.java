package ru.projectrobots;

import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.ApplicationFrame;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.model.drawer.RobotDrawer;
import ru.projectrobots.game.model.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;

public class RobotsProgram {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Robot robot = new Robot(10, 10);
        RobotDrawer robotDrawer = new RobotDrawer();
        Target target = new Target(100, 100);
        TargetDrawer targetDrawer = new TargetDrawer();

        GameDataContainer dataContainer = new GameDataContainer(robot, robotDrawer, target, targetDrawer);

        SwingUtilities.invokeLater(() -> {
            ApplicationFrame frame = new ApplicationFrame(dataContainer);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
