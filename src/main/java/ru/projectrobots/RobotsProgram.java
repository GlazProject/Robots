package ru.projectrobots;

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.ApplicationFrame;
import ru.projectrobots.game.model.Fireball;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.log.Logger;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RobotsProgram {

    public static void main(String[] args) {
        Repository.loadAllProperties("src/main/resources/properties");
        Logger.setIgnoreDebugMessages(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameEventBus eventBus = new GameEventBus();

        Target target = new Target(100, 100);
        Robot robot = new Robot(10, 10, eventBus)
                .setRobotHeight(100)
                .setRobotWidth(50);
        ArrayList<Fireball> fireballs = new ArrayList<>();

        GameDataContainer dataContainer = new GameDataContainer(robot, target, fireballs);
        GlobalSettings.setDefaultSprites(Repository.getGameEntities());

        SwingUtilities.invokeLater(() -> {
            ApplicationFrame frame = new ApplicationFrame(dataContainer, eventBus);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
