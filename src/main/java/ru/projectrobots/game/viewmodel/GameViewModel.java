package ru.projectrobots.game.viewmodel;

/* created by zzemlyanaya on 05/03/2023 */

import ru.projectrobots.core.events.ViewUpdateEvent;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.view.GameView;
import ru.projectrobots.log.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameViewModel {

    private final Timer timer = initTimer();

    private final Robot robot;
    private final Target target;
    private final GameView view;

    private static Timer initTimer() {
        return new Timer("Events generator", true);
    }

    public GameViewModel(GameDataContainer data) {
        robot = data.robot();
        target = data.target();
        view = new GameView(data);

        initTimerEvents();
        initUserEventListeners();
    }

    public GameView getView() {
        return view;
    }

    private void initTimerEvents() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.onUpdate(ViewUpdateEvent.REDRAW_MODEL_EVENT);
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                robot.update(target);
            }
        }, 0, 10);
    }

    private void initUserEventListeners() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Logger.debug("Clicked at " + e.getPoint());
                target.setTargetPosition(e.getPoint());
                view.repaint();
            }
        });

    }
}
