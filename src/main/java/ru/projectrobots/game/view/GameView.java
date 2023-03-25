package ru.projectrobots.game.view;

import ru.projectrobots.core.events.ViewUpdateEvent;
import ru.projectrobots.core.view.UpdatableView;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.model.Fireball;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.model.drawer.FireballDrawer;
import ru.projectrobots.game.model.drawer.GroundDrawer;
import ru.projectrobots.game.model.drawer.RobotDrawer;
import ru.projectrobots.game.model.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.function.Consumer;

import static ru.projectrobots.core.events.ViewUpdateEvent.REDRAW_MODEL_EVENT;

public class GameView extends JPanel implements UpdatableView {

    private final Robot robot;
    private final Target target;
    private final ArrayList<Fireball> fireballs;

    private final RobotDrawer robotDrawer = new RobotDrawer();
    private final TargetDrawer targetDrawer = new TargetDrawer();
    private final GroundDrawer groundDrawer = new GroundDrawer();
    private final FireballDrawer fireballDrawer = new FireballDrawer();

    private Consumer<Exception> exceptionListener;
    private boolean occurredFatalError = false;

    public GameView(GameDataContainer dataContainer) {
        robot = dataContainer.robot();
        target = dataContainer.target();
        fireballs = dataContainer.fireballs();

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
            fireballDrawer.drawAllFireballs(g2d, fireballs);
        } catch (FileNotFoundException | NoSuchFieldException e) {
            occurredFatalError = true;
            exceptionListener.accept(e);
        }
    }
}
