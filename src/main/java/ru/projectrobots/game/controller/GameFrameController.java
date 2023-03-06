package ru.projectrobots.game.controller;

/* created by zzemlyanaya on 05/03/2023 */

import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.view.GameFrame;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static ru.projectrobots.core.ClosableView.closingPanelLogic;

public class GameFrameController {

    private final GameFrame view;

    private final GameViewController gameViewController;

    public GameFrameController(Robot _robot) {
        gameViewController = new GameViewController(_robot);
        view = createGameWindow(_robot);
    }

    public GameFrame getView() {
        return view;
    }

    private GameFrame createGameWindow(Robot robot) {
        robot.setBoardSize(600, 800);

        GameFrame gameWindow = new GameFrame(gameViewController.getView());
        gameWindow.setSize(600, 800);

        gameWindow.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                super.internalFrameClosing(event);
                closingPanelLogic(event);
            }
        });

        gameWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                robot.setBoardSize(gameWindow.getWidth(), gameWindow.getHeight());
            }
        });
        return gameWindow;
    }
}
