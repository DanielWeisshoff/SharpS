package com.danielweisshoff.logger;

import java.time.LocalDate;
import java.time.LocalTime;

/*TODO
 * - appending the datetime is way to complicated
 */
public class Log {

    private final String dateTime;
    private final String message;

    public Log(String message) {
        this.message = message;

        dateTime = getCurrentTimeStamp();
    }

    private String getCurrentTimeStamp() {

        String dateStr = buildDateString();
        String timeStr = buildTimeString();
        return dateStr + "\t" + timeStr;
    }

    public String getMessage() {
        return message;
    }

    public String getLogFormat() {
        return "[" + dateTime + "]" + "  " + message + "\n";
    }

    private String buildDateString() {
        LocalDate date = LocalDate.now();
        int yyyy = date.getYear();
        String mm = toMonth(date.getMonthValue());
        int dd = date.getDayOfMonth();

        return yyyy + "/" + mm + "/" + dd;
    }

    private String buildTimeString() {
        LocalTime time = LocalTime.now();
        String hour = "" + time.getHour();
        String minute = "" + time.getMinute();
        String second = "" + time.getSecond();

        if (hour.length() == 1)
            hour = 0 + hour;

        if (minute.length() == 1)
            minute = 0 + minute;

        if (second.length() == 1)
            second = 0 + second;
        return hour + ":" + minute + ":" + second;
    }

    private String toMonth(int month) {
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