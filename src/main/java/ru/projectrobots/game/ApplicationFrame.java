package ru.projectrobots.game;

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.GameEventType;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.game.utils.MenuFactory;
import ru.projectrobots.game.view.ActionsFrame;
import ru.projectrobots.game.view.GameFrame;
import ru.projectrobots.game.viewmodel.ActionFrameViewModel;
import ru.projectrobots.game.viewmodel.GameFrameViewModel;
import ru.projectrobots.log.Logger;
import ru.projectrobots.log.view.LogView;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.function.Consumer;


public class ApplicationFrame extends JFrame {
    private static final String BG_IMAGE = "ground.background";
    private final LogView logWindow;

    public ApplicationFrame(GameDataContainer data, GameEventBus eventBus) {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(
            inset,
            inset,
            screenSize.width - inset * 2,
            screenSize.height - inset * 2
        );

        setBgImage(screenSize);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        ActionsFrame actionsFrame = new ActionFrameViewModel(eventBus).getView();
        addWindow(actionsFrame);

        GameFrame gameFrame = new GameFrameViewModel(data, eventBus, applicationFrameListener).getView();
        addWindow(gameFrame);

        logWindow = new LogView(Logger.getLogDelegate()).createLogView();
        addWindow(logWindow);
        logWindow.setVisible(false);

        MenuFactory menuFactory = new MenuFactory(this, eventBus);
        setJMenuBar(menuFactory.createMenuBar());

        gameFrame.requestFocusInWindow();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setBgImage(Dimension screenSize){
        try {
            setContentPane(new JLabel(Repository.getIcon(BG_IMAGE,
                    (int)screenSize.getHeight(),
                    (int)screenSize.getWidth())));
        } catch (FileNotFoundException | NoSuchFieldException ignored) {
        }
    }

    private final Consumer<GameEvent> applicationFrameListener = (event) -> {
        if (event.type() == GameEventType.OPEN_LOG_WINDOW)
            openLogWindowIfClosed();
    };

    private void openLogWindowIfClosed(){
        logWindow.setVisible(true);
    }

    protected void addWindow(JComponent frame) {
        frame.setAlignmentY(Component.TOP_ALIGNMENT);
        frame.setVisible(true);
        add(frame);
    }
}