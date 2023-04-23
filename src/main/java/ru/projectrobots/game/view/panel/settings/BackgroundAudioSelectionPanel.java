package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BackgroundAudioSelectionPanel extends JPanel {
    public static final String SET_COMMAND = "set";

    public BackgroundAudioSelectionPanel(ActionListener actionListener) {
        List<String> tracks = Repository.getBackgroundTracks();
        setLayout(new GridLayout(tracks.size() + 1, 1, 40, 20));

        for (String track : tracks) {
            JButton button = new JButton(track);
            button.setBorderPainted(true);

            button.setActionCommand(String.join(".", SET_COMMAND, track));
            button.addActionListener(actionListener);
            add(button);
        }

        JButton backBtn = new JButton("Назад в меню");
        backBtn.setBorderPainted(true);
        backBtn.setActionCommand(GameAction.OPEN_SETTINGS.name());
        backBtn.addActionListener(actionListener);

        add(backBtn);
    }
}
