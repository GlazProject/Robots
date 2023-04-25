package ru.projectrobots.game.view;

import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JInternalFrame {

    public GameFrame(GameView game) {
        super(Repository.getLocalePhrase("game_field", GlobalSettings.getLocale()),
                true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(game, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

}
