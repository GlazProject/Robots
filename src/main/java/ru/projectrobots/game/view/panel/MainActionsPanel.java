package ru.projectrobots.game.view.panel;

import ru.projectrobots.game.model.GameAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainActionsPanel extends JPanel {
    public MainActionsPanel(ActionListener actionListener){
        setLayout(new GridLayout(4, 1, 40, 20));

        JButton fireballBtn = new JButton("Огнешар");
        fireballBtn.setBorderPainted(true);
        fireballBtn.setActionCommand(GameAction.FIREBALL.name());
        fireballBtn.addActionListener(actionListener);

        JButton settingBtn = new JButton("Настройки мира");
        settingBtn.setBorderPainted(true);
        settingBtn.setActionCommand(GameAction.OPEN_SETTINGS.name());
        settingBtn.addActionListener(actionListener);

        add(fireballBtn);
        add(settingBtn);
    }
}
