package ru.projectrobots.game.utils;

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.GameEventType;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.ApplicationFrame;
import ru.projectrobots.log.Logger;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuFactory {
    private static final String FONT_NAME = "menu";
    private final GameEventBus eventBus;
    private final ApplicationFrame applicationFrame;
    private final Font font;

    public MenuFactory(ApplicationFrame frame, GameEventBus eventBus) {
        font = Repository.getFont(FONT_NAME);
        applicationFrame = frame;
        this.eventBus = eventBus;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        setFont(menuBar);
        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createTestMenu());
        return menuBar;
    }

    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(Repository.getLocalePhrase("view_mode", GlobalSettings.getLocale()));
        setFont(lookAndFeelMenu);
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                Repository.getLocalePhrase("view_mode_management", GlobalSettings.getLocale())
        );

        addScheme(lookAndFeelMenu, Repository.getLocalePhrase("system_scheme", GlobalSettings.getLocale()),
                UIManager.getSystemLookAndFeelClassName());
        addScheme(lookAndFeelMenu, Repository.getLocalePhrase("universal_scheme", GlobalSettings.getLocale()),
                UIManager.getCrossPlatformLookAndFeelClassName());

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = new JMenu(Repository.getLocalePhrase("tests", GlobalSettings.getLocale()));
        setFont(testMenu);
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                Repository.getLocalePhrase("test_commands", GlobalSettings.getLocale()));

        addTest(testMenu, Repository.getLocalePhrase("log_message", GlobalSettings.getLocale()),
                Repository.getLocalePhrase("test_log_message", GlobalSettings.getLocale()));

        addOpenLog(testMenu, Repository.getLocalePhrase("open_log_window", GlobalSettings.getLocale()));

        return testMenu;
    }

    private void addTest(JMenu testMenu, String name, String message) {
        JMenuItem newLogEntryItem = new JMenuItem(name, KeyEvent.VK_M);
        setFont(newLogEntryItem);
        newLogEntryItem.addActionListener((event) -> Logger.error(message));
        testMenu.add(newLogEntryItem);
    }

    private void addOpenLog(JMenu testMenu, String name){
        JMenuItem openLogBtn = new JMenuItem(name, KeyEvent.VK_L);
        setFont(openLogBtn);
        openLogBtn.addActionListener((event) -> eventBus.sendData(
                GameEvent.getEventWithoutData(GameEventType.OPEN_LOG_WINDOW)
        ));
        testMenu.add(openLogBtn);
    }

    private void addScheme(JMenu lookAndFeelMenu, String theme, String systemLookAndFeelClassName) {
        JMenuItem systemLookAndFeel = new JMenuItem(theme, KeyEvent.VK_S);
        setFont(systemLookAndFeel);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(systemLookAndFeelClassName);
            applicationFrame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(applicationFrame);
        } catch (ClassNotFoundException
                 | InstantiationException
                 | IllegalAccessException
                 | UnsupportedLookAndFeelException ignored) {
            Logger.error("Error while setting up settings");
        }
    }

    private void setFont(JComponent component){
        if (font != null)
            component.setFont(font);
    }
}