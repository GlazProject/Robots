package ru.projectrobots.game.controller;

/* created by zzemlyanaya on 05/03/2023 */

import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.view.GameView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameViewController {

    private final Timer timer = initTimer();

    private final Robot robot;
    private final GameView view;

    private static Timer initTimer() {
        return new Timer("Events generator", true);
    }

    public GameViewController(Robot _robot) {
        robot = _robot;
        view = new GameView(robot);

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
                view.onRedrawEvent();
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                view.onModelUpdateEvent();
            }
        }, 0, 10);
    }

    private void initUserEventListeners() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robot.setTargetPosition(e.getPoint());
                view.repaint();
            }
        });
    }


}
