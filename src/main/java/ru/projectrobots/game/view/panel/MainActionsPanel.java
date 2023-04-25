package ru.projectrobots.game.view.panel;

import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainActionsPanel extends JPanel {
    public MainActionsPanel(ActionListener actionListener){
        setLayout(new GridLayout(4, 1, 40, 20));

        JButton fireballBtn = new JButton(Repository.getLocalePhrase("fireball", GlobalSettings.getLocale()));
        fireballBtn.setBorderPainted(true);
        fireballBtn.setActionCommand(GameAction.FIREBALL.name());
        fireballBtn.addActionListener(actionListener);

        JButton settingBtn = new JButton(Repository.getLocalePhrase("game_settings", GlobalSettings.getLocale()));
        settingBtn.setBorderPainted(true);
        settingBtn.setActionCommand(GameAction.OPEN_SETTINGS.name());
        settingBtn.addActionListener(actionListener);

        add(fireballBtn);
        add(settingBtn);
    }
}
