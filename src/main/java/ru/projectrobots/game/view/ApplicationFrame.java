package ru.projectrobots.game.view;

import ru.projectrobots.game.controller.GameFrameController;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.utils.MenuFactory;
import ru.projectrobots.log.Logger;
import ru.projectrobots.log.view.LogView;

import javax.swing.*;
import java.awt.*;


public class ApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public ApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(
            inset,
            inset,
            screenSize.width - inset * 2,
            screenSize.height - inset * 2
        );

        setContentPane(desktopPane);


        LogView logView = new LogView(Logger.getLogDelegate()).createLogView();
        addWindow(logView);

        GameFrame gameWindow = new GameFrameController(new Robot()).getView();
        addWindow(gameWindow);

        MenuFactory menuFactory = new MenuFactory(this);
        setJMenuBar(menuFactory.createMenuBar());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}