package ru.projectrobots.log;

import ru.projectrobots.log.delegate.LogDelegate;
import ru.projectrobots.log.model.LogLevel;

public final class Logger {
    private static final LogDelegate logDelegate;
    private static boolean ignoreDebugMessages = false;

    static {
        logDelegate = new LogDelegate(40);
    }

    private Logger() {
    }

    public static void setIgnoreDebugMessages(boolean value){
        ignoreDebugMessages = value;
    }

    public static void debug(String strMessage) {
        if (ignoreDebugMessages) return;
        logDelegate.addEntry(LogLevel.Debug, strMessage);
    }

    public static void error(String strMessage) {
        logDelegate.addEntry(LogLevel.Error, strMessage);
    }

    public static LogDelegate getLogDelegate() {
        return logDelegate;
    }
}
