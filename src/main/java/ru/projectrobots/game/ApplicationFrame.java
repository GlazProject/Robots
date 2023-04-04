package ru.projectrobots.game;

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.utils.MenuFactory;
import ru.projectrobots.game.view.ActionsFrame;
import ru.projectrobots.game.view.GameFrame;
import ru.projectrobots.game.viewmodel.ActionFrameViewModel;
import ru.projectrobots.game.viewmodel.GameFrameViewModel;
import ru.projectrobots.log.Logger;
import ru.projectrobots.log.view.LogView;

import javax.swing.*;
import java.awt.*;


public class ApplicationFrame extends JFrame {

    public ApplicationFrame(GameDataContainer data, GameEventBus eventBus) {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(
            inset,
            inset,
            screenSize.width - inset * 2,
            screenSize.height - inset * 2
        );

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        ActionsFrame actionsFrame = new ActionFrameViewModel(eventBus).getView();
        addWindow(actionsFrame);


        GameFrame gameWindow = new GameFrameViewModel(data, eventBus).getView();
        addWindow(gameWindow);

        LogView logView = new LogView(Logger.getLogDelegate()).createLogView();
        addWindow(logView);

        MenuFactory menuFactory = new MenuFactory(this);
        setJMenuBar(menuFactory.createMenuBar());

        gameWindow.requestFocusInWindow();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected void addWindow(JInternalFrame frame) {
        frame.setAlignmentY(Component.TOP_ALIGNMENT);
        frame.setVisible(true);
        add(frame);
    }
}