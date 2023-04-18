package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

public class EntityLooksSelectionPanel extends JPanel {
    public static final String PREVIEW = "preview";
    public static final String SET_COMMAND = "set";

    public EntityLooksSelectionPanel(ActionListener actionListener, String entityName) {
        List<String> looks = Repository.getLooks(entityName);
        setLayout(new GridLayout(looks.size() + 1, 1, 40, 20));

        for (String look:looks) {
            JButton button = new JButton(look);
            button.setBorderPainted(true);

            try {
                button.setIcon(Repository.getIconWithHeight(
                        String.join(".", entityName, look, PREVIEW),
                        80));
            } catch (NoSuchFieldException | FileNotFoundException ignored){
            }

            button.setActionCommand(String.join(".", SET_COMMAND, entityName, look));
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
