package ru.projectrobots.game.view.panel;

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.game.view.panel.settings.BackgroundAudioSelectionPanel;
import ru.projectrobots.game.view.panel.settings.EntityLooksSelectionPanel;
import ru.projectrobots.game.view.panel.settings.GameSettingsPanel;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainGameSettingsPanel extends JPanel {
    private final CardLayout layout = new CardLayout();
    private final JPanel contentPane = this;

    private final ActionListener actionListener;

    private final List<String> entities = Repository.getGameEntities();
    public static final String BACKGROUND = "background";

    private final ActionListener panelSwitchListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), GameAction.OPEN_SETTINGS.name())){
                layout.first(contentPane);
                return;
            }

            if (!entities.contains(e.getActionCommand()) && !Objects.equals(e.getActionCommand(), BACKGROUND)){
                actionListener.actionPerformed(e);
                return;
            }

            layout.first(contentPane);
            for(String s : entities) {
                layout.next(contentPane);
                if (Objects.equals(e.getActionCommand(), s))
                    return;
            }
            layout.next(contentPane);
        }
    };

    public MainGameSettingsPanel(ActionListener actionListener){
        this.actionListener = actionListener;
        setLayout(layout);

        ArrayList<String> settings = new ArrayList<>(entities);
        settings.add(BACKGROUND);

        JPanel basePanel = new GameSettingsPanel(panelSwitchListener, settings);
        add(basePanel);

        for(String entity : entities){
            JPanel panel = new EntityLooksSelectionPanel(panelSwitchListener, entity);
            add(panel);
        }
        JPanel bgPanel = new BackgroundAudioSelectionPanel(panelSwitchListener);
        add(bgPanel);
    }
}
