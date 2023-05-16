package ru.projectrobots.core.view;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.function.Consumer;

public class DialogFactory {
    public static void showCloseDialog(InternalFrameEvent event, Consumer<Object> onClosedCallback) {
        String[] options = {"Да", "Нет"};

        if (getResultForOption(event, options) == 0) {
            event.getInternalFrame().setVisible(false);
            onClosedCallback.accept(event);
        }
        event.getInternalFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public static void showErrorDialog(Component component, Exception exception){
        String[] options = {"Ок"};
        int result = JOptionPane.showOptionDialog(
                component,
                "Возникла непредвиденная ошибка\n" + exception.toString() + "\nПрограмма будет закрыта", "Ошибка",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null, options, options[0]
        );
        if (result == 0){
            component.setVisible(false);
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