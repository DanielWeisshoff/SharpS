package logger;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {

    public final String message;
    public final Channel channel;
    public LocalDate date;
    public LocalTime time;

    public Log(String message, Channel channel) {
        this.message = message;
        this.channel = channel;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }
}