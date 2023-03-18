package logger;

import java.util.ArrayList;

import utils.Goethe;

/**Buffered Logger that subdivides messages into channels*/
public class Logger {

    private static final ArrayList<Log> logBuffer = new ArrayList<>();

    public static void log(String msg) {
        Logger.log(msg, Channel.ANONYMOUS);
    }

    public static void log(String msg, Channel channel) {
        logBuffer.add(new Log(msg, channel));
    }

    public static void writeBufferToFile() {
        Goethe.writeLogs(logBuffer);
    }

    public static void clearLogFile() {
        Goethe.writeText(Goethe.logPath, "", false);
    }

    public static void clearBuffer() {
        logBuffer.clear();
    }
}
