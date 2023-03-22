package ru.projectrobots.log.view;

import ru.projectrobots.log.Logger;
import ru.projectrobots.log.delegate.LogDelegate;
import ru.projectrobots.log.listeners.LogChangeListener;
import ru.projectrobots.log.model.LogEntry;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;

import static ru.projectrobots.core.view.ClosingView.closingPanelLogic;


public class LogView extends JInternalFrame implements LogChangeListener {

    private final LogDelegate logDelegate;
    private final TextArea contentView;

    public LogView(LogDelegate _logDelegate) {
        super("Протокол работы", true, true, true, true);

        logDelegate = _logDelegate;
        logDelegate.registerListener(this);

        contentView = new TextArea("");
        contentView.setMinimumSize(new Dimension(300, 600));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(contentView, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void doDefaultCloseAction() {
        unregisterListener();
        super.doDefaultCloseAction();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logDelegate.getLog()) {
            content.append(entry.getMessage()).append("\n");
        }
        contentView.setText(content.toString());
        contentView.invalidate();
    }

    private void unregisterListener() {
        logDelegate.unregisterListener(this);
    }

    public LogView createLogView() {
        setLocation(10, 10);
        setSize(500, 800);

        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                super.internalFrameClosing(event);
                closingPanelLogic(event);
                unregisterListener();
            }
        });

        pack();
        Logger.debug("Протокол работает");
        return this;
    }
}
