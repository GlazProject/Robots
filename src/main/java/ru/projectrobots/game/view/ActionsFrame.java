package ru.projectrobots.game.view;

/* created by zzemlyanaya on 25/03/2023 */

import ru.projectrobots.game.model.GameAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionsFrame extends JInternalFrame {

    public ActionsFrame(ActionListener actionListener) {
        super("Способности", false, true, false, true);

        setLayout(new GridLayout(4, 1, 40, 20));
        JButton fireballBtn = new JButton("Огнешар");
        fireballBtn.setBorderPainted(true);
        fireballBtn.setActionCommand(GameAction.FIREBALL.name());
        fireballBtn.addActionListener(actionListener);

        add(fireballBtn);
    }

}
