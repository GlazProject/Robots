package ru.projectrobots.game.utils;

import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.ApplicationFrame;
import ru.projectrobots.log.Logger;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuFactory {

    private final ApplicationFrame applicationFrame;

    public MenuFactory(ApplicationFrame frame) {
        applicationFrame = frame;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createTestMenu());
        return menuBar;
    }

    private JMenu createLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(Repository.getLocalePhrase("view_mode", GlobalSettings.getLocale()));
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
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                Repository.getLocalePhrase("test_commands", GlobalSettings.getLocale()));

        addTest(testMenu, Repository.getLocalePhrase("log_message", GlobalSettings.getLocale()),
                Repository.getLocalePhrase("new_line", GlobalSettings.getLocale()));

        return testMenu;
    }

    private void addTest(JMenu testMenu, String name, String message) {
        JMenuItem newLogEntryItem = new JMenuItem(name, KeyEvent.VK_S);
        newLogEntryItem.addActionListener((event) -> Logger.error(message));
        testMenu.add(newLogEntryItem);
    }

    private void addScheme(JMenu lookAndFeelMenu, String theme, String systemLookAndFeelClassName) {
        JMenuItem systemLookAndFeel = new JMenuItem(theme, KeyEvent.VK_S);
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
}