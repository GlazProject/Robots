package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.core.view.GameJButton;
import ru.projectrobots.core.view.TransparentJPanel;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class BackgroundAudioSelectionPanel extends TransparentJPanel {
    public static final String SET_COMMAND = "set.music";

    public BackgroundAudioSelectionPanel(ActionListener actionListener) {
        List<String> tracks = new java.util.ArrayList<>(Repository.getBackgroundTracks());
        tracks.add("no_music");
        int rows = Math.max(tracks.size() + 1, 5);
        setLayout(new GridLayout(rows, 1, 40, 20));

        for (String track : tracks) {
            GameJButton button = new GameJButton(Repository.getLocalePhrase(track, GlobalSettings.getLocale()));
            button.setBorderPainted(true);

            button.setActionCommand(String.join(".", SET_COMMAND, track));
            button.addActionListener(actionListener);
            add(button);
        }

        GameJButton backBtn = new GameJButton(Repository.getLocalePhrase("back_to_menu", GlobalSettings.getLocale()));
        backBtn.setBorderPainted(true);
        backBtn.setActionCommand(GameAction.OPEN_SETTINGS.name());
        backBtn.addActionListener(actionListener);

        add(backBtn);
    }
}
