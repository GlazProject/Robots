package ru.projectrobots.game.view;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JInternalFrame {

    public GameFrame(GameView game) {
        super("Игровое поле", true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(game, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

}
