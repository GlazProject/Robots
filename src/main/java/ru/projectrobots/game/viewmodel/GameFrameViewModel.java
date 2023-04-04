package ru.projectrobots.game.viewmodel;

/* created by zzemlyanaya on 05/03/2023 */

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.GameEventType;
import ru.projectrobots.core.events.ViewUpdateEvent;
import ru.projectrobots.core.view.DialogFactory;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.view.GameFrame;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static ru.projectrobots.core.view.DialogFactory.showCloseDialog;

public class GameFrameViewModel {

    private final GameFrame view;
    private final GameViewModel gameViewModel;

    private final GameEventBus eventBus;

    public GameFrameViewModel(GameDataContainer data, GameEventBus eventBus) {
        this.eventBus = eventBus;

        gameViewModel = new GameViewModel(data, eventBus);
        view = createGameWindow(data.robot(), data.target());
    }

    public GameFrame getView() {
        return view;
    }

    private GameFrame createGameWindow(Robot robot, Target target) {
        robot.setBoardSize(600, 800);
        target.setBoardSize(600, 800);

        GameFrame gameWindow = new GameFrame(gameViewModel.getView());
        gameWindow.setPreferredSize(new Dimension(600, 800));

        gameViewModel.getView().addExceptionListener(e -> {
            eventBus.sendData(GameEvent.getEventWithoutData(GameEventType.GAME_CLOSED));

            DialogFactory.showErrorDialog(gameWindow, e);
            gameWindow.setIgnoreRepaint(true);
        });

        gameWindow.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                super.internalFrameClosing(event);
                showCloseDialog(event, t -> eventBus.sendData(GameEvent.getEventWithoutData(GameEventType.GAME_CLOSED)));
            }
        });

        gameWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                robot.setBoardSize(gameWindow.getWidth(), gameWindow.getHeight());
                target.setBoardSize(gameWindow.getWidth(), gameWindow.getHeight());
                gameViewModel.getView().onUpdate(ViewUpdateEvent.REDRAW_MODEL_EVENT);
            }
        });
        return gameWindow;
    }
}
