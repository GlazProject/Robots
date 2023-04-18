package ru.projectrobots.game.view;

/* created by zzemlyanaya on 25/03/2023 */

import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.game.view.panel.MainGameSettingsPanel;
import ru.projectrobots.game.view.panel.MainActionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ActionsFrame extends JInternalFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel contentPane = new JPanel();
    private final ActionListener actionListener;
    private final ActionListener panelSwitchListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), GameAction.OPEN_SETTINGS.name())) {
                layout.next(contentPane);
                return;
            }

            if (Objects.equals(e.getActionCommand(), GameAction.CLOSE_SETTINGS.name())) {
                layout.next(contentPane);
                return;
            }

            actionListener.actionPerformed(e);
        }
    };

    public ActionsFrame(ActionListener actionListener) {
        super("Способности", false, true, false, true);

        this.actionListener = actionListener;
        contentPane.setLayout(layout);

        JPanel mainPanel = new MainActionsPanel(panelSwitchListener);
        JPanel settingsPanel = new MainGameSettingsPanel(panelSwitchListener);

        contentPane.add(mainPanel, "Способности персонажа");
        contentPane.add(settingsPanel, "Настройки игры");

        setContentPane(contentPane);
    }
}
