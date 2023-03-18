package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import lexer.Token;
import logger.Channel;
import logger.Log;

/**
 * Responsible for all file writing and reading
 */
public class Goethe {

    public static final File lexerPath = new File("src/lexer.log");
    public static final File logPath = new File("src/log.log");
    public static final File programPath = new File("src/program.#s"); //ß = \u00df

    public static String getText() {
        StringBuilder program = new StringBuilder();
        try {
            BufferedReader scanner = new BufferedReader(new FileReader(programPath));//.useDelimiter("(\\b|\\B)");

            String line;
            while ((line = scanner.readLine()) != null)
                program.append(line + '\n');
            program.append("\0");

            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return program.toString();
    }

    public static String getLine(int lineNumber) {
        String line = "";
        try {
            BufferedReader scanner = new BufferedReader(new FileReader(programPath));//.useDelimiter("(\\b|\\B)");

            int curLine = 0;
            while ((line = scanner.readLine()) != null) {
                curLine++;
                if (curLine == lineNumber)
                    break;
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return line;
    }

    public static void writeText(File file, String text, boolean append) {
        try {
            FileWriter myWriter = new FileWriter(file, append);
            myWriter.write(text);
            myWriter.close();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static void writeTokens(Token[] tokens) {
        StringBuilder tokenStr = new StringBuilder();
        for (Token t : tokens) {
            tokenStr.append(t.getDescription());
            tokenStr.append("\n");
        }
        writeText(lexerPath, tokenStr.toString(), false);
    }

    public static void writeLogs(ArrayList<Log> logs) {
        try {
            PrintWriter pw = new PrintWriter(logPath);

            Channel lastChannel = Channel.ANONYMOUS;
            for (Log l : logs) {
                if (l.channel != lastChannel) {
                    pw.println();
                    lastChannel = l.channel;
                }
                pw.println(getFormattedMessage(l));
            }
            pw.close();
        } catch (Exception e) {
        }
    }

    //TODO eigene Klasse

    private static String getFormattedMessage(Log log) {
        String dateTime = buildDateString() + "|" + buildTimeString();
        if (log.channel == Channel.ANONYMOUS)
            return "[" + dateTime + "]" + " " + log.message;
        else
            return "[" + dateTime + "] " + log.channel + ": " + log.message;
    }

    private static String buildDateString() {
        LocalDate date = LocalDate.now();

        int yyyy = date.getYear();
        String mm = toMonth(date.getMonthValue());
        int dd = date.getDayOfMonth();

        return yyyy + "/" + mm + "/" + dd;
    }

    private static String buildTimeString() {
        LocalTime time = LocalTime.now();
        String hourStr = "" + time.getHour();
        String minuteStr = "" + time.getMinute();
        String secondStr = "" + time.getSecond();

        if (hourStr.length() == 1)
            hourStr = 0 + hourStr;

        if (minuteStr.length() == 1)
            minuteStr = 0 + minuteStr;

        if (secondStr.length() == 1)
            secondStr = 0 + secondStr;

        return hourStr + ":" + minuteStr + ":" + secondStr;
    }

    private static String toMonth(int month) {
        return switch (month) {
        case 1 -> "JAN";
        case 2 -> "FEB";
        case 3 -> "MAR";
        case 4 -> "APR";
        case 5 -> "MAY";
        case 6 -> "JUN";
        case 7 -> "JUL";
        case 8 -> "AUG";
        case 9 -> "SEP";
        case 10 -> "OCT";
        case 11 -> "NOV";
        case 12 -> "DEC";
        default -> "ERR";
        };
    }
}
