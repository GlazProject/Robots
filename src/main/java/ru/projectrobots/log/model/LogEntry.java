package ru.projectrobots.log.model;

public class LogEntry {
    private final LogLevel logLevel;
    private final String message;

    public LogEntry(LogLevel logLevel, String message) {
        this.message = message;
        this.logLevel = logLevel;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLevel() {
        return logLevel;
    }
}

