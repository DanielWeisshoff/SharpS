package logger;

import java.util.ArrayList;

import utils.Goethe;

public class Logger {

    public static boolean enabled = false;

    private static final ArrayList<Log> messages = new ArrayList<>();

    //TODO extract into own file
    public enum Channel {
        LEXER, PARSER, INTERPRETER, DEBUG, ANONYMOUS
    }

    public static void log(String msg) {
        Logger.log(msg, Channel.ANONYMOUS);
    }

    public static void log(String msg, Channel channel) {
        if (enabled)
            messages.add(new Log(msg, channel));
    }

    //TODO urgh
    public static void insertSeparator(String msg) {
        String formatted = "==================================================\n" + msg
                + "==================================================";
        Logger.log(formatted);
    }

    public static void writeLogs() {
        Goethe.writeLogs(messages);
    }

    public static void clearLogs() {
        Goethe.writeText(Goethe.logPath, "", false);
    }

    public static void printAll() {
        for (Log l : messages) {
            System.out.println(l.getMessage());
        }
    }

    public static void clear() {
        messages.clear();
    }
}
