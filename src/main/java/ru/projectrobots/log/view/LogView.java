package ru.projectrobots.log.view;

import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.log.Logger;
import ru.projectrobots.log.delegate.LogDelegate;
import ru.projectrobots.log.listeners.LogChangeListener;
import ru.projectrobots.log.model.LogEntry;
import ru.projectrobots.resources.Repository;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.io.FileNotFoundException;

import static ru.projectrobots.core.view.DialogFactory.showCloseDialog;


public class LogView extends JInternalFrame implements LogChangeListener {
    private static final String ICON = "game.log.icon";
    private final LogDelegate logDelegate;
    private final TextArea contentView;

    public LogView(LogDelegate _logDelegate) {
        super(Repository.getLocalePhrase("log_window_name", GlobalSettings.getLocale()),
                true, true, true, true);
        setIcon();

        logDelegate = _logDelegate;
        logDelegate.registerListener(this);

        contentView = new TextArea("");
        contentView.setMinimumSize(new Dimension(300, 600));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(contentView, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    private void setIcon(){
        try {
            setFrameIcon(Repository.getIcon(ICON, 80, 80));
        } catch (FileNotFoundException | NoSuchFieldException ignored) {
        }
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
        setPreferredSize(new Dimension(400, 800));

        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                super.internalFrameClosing(event);
                showCloseDialog(event, t -> unregisterListener());
            }
        });

        pack();
        Logger.debug("All right. Test message");
        return this;
    }
}
