package ru.projectrobots.game.view;

import ru.projectrobots.core.events.ViewUpdateEvent;
import ru.projectrobots.core.view.UpdatableView;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.model.drawer.GroundDrawer;
import ru.projectrobots.game.model.drawer.RobotDrawer;
import ru.projectrobots.game.model.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

import static ru.projectrobots.core.events.ViewUpdateEvent.REDRAW_MODEL_EVENT;

public class GameView extends JPanel implements UpdatableView {

    private final Robot robot;
    private final Target target;
    private final RobotDrawer robotDrawer;
    private final TargetDrawer targetDrawer;
    private final GroundDrawer groundDrawer;
    private Consumer<Exception> exceptionListener;
    private boolean occurredFatalError = false;

    public GameView(GameDataContainer dataContainer) {
        robot = dataContainer.robot();
        target = dataContainer.target();
        robotDrawer = dataContainer.robotDrawer();
        targetDrawer = dataContainer.targetDrawer();
        groundDrawer = dataContainer.groundDrawer();

        setDoubleBuffered(true);
    }

    @Override
    public void onUpdate(ViewUpdateEvent event) {
        if (event == REDRAW_MODEL_EVENT) onRedrawEvent();
    }

    private void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    public void addExceptionListener(Consumer<Exception> listener){
        this.exceptionListener = listener;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (occurredFatalError) return;
        Graphics2D g2d = (Graphics2D) g;
        try {
            groundDrawer.drawGround(g2d, this);
            targetDrawer.drawTarget(g2d, target);
            robotDrawer.drawRobot(g2d, robot);
        } catch (FileNotFoundException | NoSuchFieldException e) {
            occurredFatalError = true;
            exceptionListener.accept(e);
        }
    }
}
