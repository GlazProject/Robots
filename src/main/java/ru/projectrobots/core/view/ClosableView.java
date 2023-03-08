package ru.projectrobots.core.view;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;

public class ClosableView {

    public static void closingPanelLogic(InternalFrameEvent event) {
        String[] options = {"Да", "Нет"};

        if (getResultForOption(event, options) == 0) {
            event.getInternalFrame().setVisible(false);
        }
        event.getInternalFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private static int getResultForOption(InternalFrameEvent e, String[] options) {
        return JOptionPane.showOptionDialog(
            e.getInternalFrame(),
            "Закрыть окно", "Подтверждение",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[1]
        );
    }
}