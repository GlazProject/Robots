package ru.projectrobots.core.view;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

public class ClosingView {
    public static void closingPanelLogic(InternalFrameEvent event) {
        String[] options = {"Да", "Нет"};

        if (getResultForOption(event, options) == 0) {
            event.getInternalFrame().setVisible(false);
        }
        event.getInternalFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public static void exceptionPanelLogic(Component mainFrame, Exception exception){
        String[] options = {"Ок"};
        mainFrame.setIgnoreRepaint(true);
        int result = JOptionPane.showOptionDialog(
                mainFrame,
                "Возникла непредвиденная ошибка\n" + exception.toString() + "\nПрограмма будет закрыта", "Ошибка",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null, options, options[0]
        );
        if (result == 0){
            mainFrame.setVisible(false);
        }
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