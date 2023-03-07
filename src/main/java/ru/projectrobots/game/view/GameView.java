package ru.projectrobots.game.view;

import ru.projectrobots.core.events.ViewUpdateEvent;
import ru.projectrobots.core.view.UpdatableView;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.model.drawer.RobotDrawer;
import ru.projectrobots.game.model.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;

import static ru.projectrobots.core.events.ViewUpdateEvent.REDRAW_MODEL_EVENT;

public class GameView extends JPanel implements UpdatableView {

    private final Robot robot;
    private final Target target;
    private final RobotDrawer robotDrawer;
    private final TargetDrawer targetDrawer;

    public GameView(GameDataContainer dataContainer) {
        robot = dataContainer.robot();
        target = dataContainer.target();
        robotDrawer = dataContainer.robotDrawer();
        targetDrawer = dataContainer.targetDrawer();

        setDoubleBuffered(true);
    }

    @Override
    public void onUpdate(ViewUpdateEvent event) {
        if (event == REDRAW_MODEL_EVENT) onRedrawEvent();
    }

    private void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        robotDrawer.drawRobot(g2d, robot);
        targetDrawer.drawTarget(g2d, target);
    }
}
