package ru.projectrobots.game.view.panel;

import ru.projectrobots.core.view.GameJButton;
import ru.projectrobots.core.view.TransparentJPanel;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import java.awt.*;
import java.awt.event.ActionListener;

public class MainActionsPanel extends TransparentJPanel {
    public MainActionsPanel(ActionListener actionListener){
        setLayout(new GridLayout(4, 1, 40, 20));

        GameJButton fireballBtn = new GameJButton(Repository.getLocalePhrase("fireball", GlobalSettings.getLocale()));
        fireballBtn.setBorderPainted(true);
        fireballBtn.setActionCommand(GameAction.FIREBALL.name());
        fireballBtn.addActionListener(actionListener);

        GameJButton settingBtn = new GameJButton(Repository.getLocalePhrase("game_settings", GlobalSettings.getLocale()));
        settingBtn.setBorderPainted(true);
        settingBtn.setActionCommand(GameAction.OPEN_SETTINGS.name());
        settingBtn.addActionListener(actionListener);

        add(fireballBtn);
        add(settingBtn);
    }
}
