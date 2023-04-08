package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameSettingsPanel extends JPanel {
    public GameSettingsPanel(ActionListener actionListener){
        List<String> entities = ResourceManager.getGameEntities();
        setLayout(new GridLayout(entities.size() + 1, 1));

        for(String entity : entities){
            JButton selectHeroBtn = new JButton("Select " + entity);
            selectHeroBtn.setBorderPainted(true);
            selectHeroBtn.setActionCommand(entity);
            selectHeroBtn.addActionListener(actionListener);
            add(selectHeroBtn);
        }


        JButton backBtn = new JButton("Назад к способностям");
        backBtn.setBorderPainted(true);
        backBtn.setActionCommand(GameAction.CLOSE_SETTINGS.name());
        backBtn.addActionListener(actionListener);

        add(backBtn);
    }
}
