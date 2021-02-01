package com.danielweisshoff.logger;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/*TODO
 * - Way of writing to textfile is very unoptimized
 */
public class Logger {

    private static final ArrayList<Log> messages = new ArrayList<>();
    private static final String filePath = "C:\\Users\\danie\\Desktop\\log.txt";
    public static boolean logInConsole = false;
    private static File logFile;

    public static void log(Object o) {
        String msg = o.toString();
        Log l = new Log(msg);
        messages.add(l);
        writeToFile(l);
    }

    public static void printAll() {
        for (Log l : messages) {
            System.out.println(l.getMessage());
        }
    }

    public static void clear() {
        messages.clear();
    }

    public static void writeToFile(Log l) {
        try {
            if (logFile == null) {
                logFile = new File(filePath);
            }

            FileWriter logWriter = new FileWriter(filePath, true);
            logWriter.write(l.getLogFormat());
            logWriter.close();

        } catch (Exception pikachu) {
            System.out.println(pikachu.getLocalizedMessage());
        }
    }
}
