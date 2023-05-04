package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.core.view.GameJButton;
import ru.projectrobots.core.view.TransparentJPanel;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameSettingsPanel extends TransparentJPanel {
    public GameSettingsPanel(ActionListener actionListener, List<String> entities){
        setLayout(new GridLayout(entities.size() + 1, 1));

        for(String entity : entities){
            GameJButton selectHeroBtn = new GameJButton(Repository.getLocalePhrase(entity, GlobalSettings.getLocale()));
            selectHeroBtn.setBorderPainted(true);
            selectHeroBtn.setActionCommand(entity);
            selectHeroBtn.addActionListener(actionListener);
            add(selectHeroBtn);
        }

        GameJButton backBtn = new GameJButton(Repository.getLocalePhrase("back_to_abilities", GlobalSettings.getLocale()));
        backBtn.setBorderPainted(true);
        backBtn.setActionCommand(GameAction.CLOSE_SETTINGS.name());
        backBtn.addActionListener(actionListener);

        add(backBtn);
    }
}
