package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.ResourceManager;
import ru.projectrobots.resources.ResourceProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

public class EntityLooksSelectionPanel extends JPanel {
    public EntityLooksSelectionPanel(ActionListener actionListener, String entityName) {
        List<String> looks = ResourceManager.getLooks(entityName);
        setLayout(new GridLayout(looks.size()+1, 1, 40, 20));

        for (String look:looks) {
            JButton button = new JButton(look);
            button.setBorderPainted(true);

            try {
                button.setIcon(ResourceProvider.getIconWithHeight(look + ".preview", 80));
            } catch (NoSuchFieldException | FileNotFoundException ignored){
            }

            button.setActionCommand("set." + entityName + "." + look);
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
