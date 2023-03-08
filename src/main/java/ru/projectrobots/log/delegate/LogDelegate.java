package ru.projectrobots.log.delegate;

import ru.projectrobots.log.listeners.LogChangeListener;
import ru.projectrobots.log.model.LogEntry;
import ru.projectrobots.log.model.LogLevel;
import ru.projectrobots.log.model.LogQueue;

import java.util.ArrayList;

public class LogDelegate {

    private final LogQueue logQueue;

    private final ArrayList<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;

    public LogDelegate(int queueLength) {
        logQueue = new LogQueue(queueLength);
        listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            activeListeners = null;
        }
    }

    public void unregisterListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            activeListeners = null;
        }
    }

    public void addEntry(LogLevel logLevel, String message) {
        logQueue.addEntry(logLevel, message);

        LogChangeListener[] activeListeners = this.activeListeners;
        if (activeListeners == null) {
            synchronized (listeners) {
                if (this.activeListeners == null) {
                    activeListeners = listeners.toArray(new LogChangeListener[0]);
                    this.activeListeners = activeListeners;
                }
            }
        }

        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }

    public Iterable<LogEntry> getLog() {
        return logQueue.getEntries();
    }
}
