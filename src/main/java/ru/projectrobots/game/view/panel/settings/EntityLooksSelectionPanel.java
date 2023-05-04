package ru.projectrobots.game.view.panel.settings;

import ru.projectrobots.core.view.GameJButton;
import ru.projectrobots.core.view.TransparentJPanel;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.resources.Repository;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

public class EntityLooksSelectionPanel extends TransparentJPanel {
    public static final String PREVIEW = "preview";
    public static final String SET_COMMAND = "set.look";

    public EntityLooksSelectionPanel(ActionListener actionListener, String entityName) {
        List<String> looks = Repository.getLooks(entityName);
        int rows = Math.max(looks.size() + 1, 5);
        setLayout(new GridLayout(rows, 1, 40, 20));

        for (String look:looks) {
            GameJButton button = new GameJButton(Repository.getLocalePhrase(look, GlobalSettings.getLocale()));
            button.setBorderPainted(true);

            try {
                button.setIcon(Repository.getIconWithHeight(
                        String.join(".", entityName, look, PREVIEW),
                        75));
            } catch (NoSuchFieldException | FileNotFoundException ignored){
            }

            button.setActionCommand(String.join(".", SET_COMMAND, entityName, look));
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
