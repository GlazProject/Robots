package ru.projectrobots.game.view.panel;

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.game.view.panel.settings.EntityLooksSelectionPanel;
import ru.projectrobots.game.view.panel.settings.GameSettingsPanel;
import ru.projectrobots.resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class MainGameSettingsPanel extends JPanel {
    private final CardLayout layout = new CardLayout();
    private final JPanel contentPane = this;
    private final ActionListener actionListener;
    private final List<String> entities = ResourceManager.getGameEntities();

    private final ActionListener panelSwitchListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), GameAction.OPEN_SETTINGS.name())){
                layout.first(contentPane);
                return;
            }

            if (!entities.contains(e.getActionCommand())){
                actionListener.actionPerformed(e);
                return;
            }

            layout.first(contentPane);
            for(String s : entities) {
                layout.next(contentPane);
                if (Objects.equals(e.getActionCommand(), s))
                    return;
            }
        }
    };

    public MainGameSettingsPanel(ActionListener actionListener){
        this.actionListener = actionListener;
        setLayout(layout);

        JPanel basePanel = new GameSettingsPanel(panelSwitchListener);
        add(basePanel);

        for(String entity : entities){
            JPanel panel = new EntityLooksSelectionPanel(panelSwitchListener, entity);
            add(panel);
        }
    }
}
