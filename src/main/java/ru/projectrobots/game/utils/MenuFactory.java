package ru.projectrobots.game.utils;

import ru.projectrobots.game.ApplicationFrame;
import ru.projectrobots.log.Logger;

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
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");

        addScheme(lookAndFeelMenu, "Системная схема", UIManager.getSystemLookAndFeelClassName());
        addScheme(lookAndFeelMenu, "Универсальная схема", UIManager.getCrossPlatformLookAndFeelClassName());

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");

        addTest(testMenu, "Сообщение в лог", "Новая строка");

        return testMenu;
    }

    private void addTest(JMenu testMenu, String name, String message) {
        JMenuItem newLogEntryItem = new JMenuItem(name, KeyEvent.VK_S);
        newLogEntryItem.addActionListener((event) -> Logger.debug(message));
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