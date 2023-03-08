package ru.projectrobots.log.model;

/* created by zzemlyanaya on 05/03/2023 */

import java.util.concurrent.ConcurrentLinkedQueue;

public class LogQueue {
    private final int queueLength;
    private final ConcurrentLinkedQueue<LogEntry> entries;


    public LogQueue(int queueLength) {
        this.queueLength = queueLength;
        entries = new ConcurrentLinkedQueue<>();
    }

    public void addEntry(LogLevel logLevel, String message) {
        if (getQueueSize() == queueLength) {
            entries.remove();
        }

        LogEntry entry = new LogEntry(logLevel, message);
        entries.add(entry);
    }

    public int getQueueSize() {
        return entries.size();
    }

    public ConcurrentLinkedQueue<LogEntry> getEntries() {
        return entries;
    }
}
