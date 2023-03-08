package ru.projectrobots.game.viewmodel;

/* created by zzemlyanaya on 05/03/2023 */

import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.view.GameFrame;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static ru.projectrobots.core.view.ClosableView.closingPanelLogic;

public class GameFrameViewModel {

    private final GameFrame view;
    private final GameViewModel gameViewModel;

    public GameFrameViewModel(GameDataContainer data) {
        gameViewModel = new GameViewModel(data);
        view = createGameWindow(data.robot(), data.target());
    }

    public GameFrame getView() {
        return view;
    }

    private GameFrame createGameWindow(Robot robot, Target target) {
        robot.setBoardSize(600, 800);
        target.setBoardSize(600, 800);

        GameFrame gameWindow = new GameFrame(gameViewModel.getView());
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
                target.setBoardSize(gameWindow.getWidth(), gameWindow.getHeight());
            }
        });
        return gameWindow;
    }
}
