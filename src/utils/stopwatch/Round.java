package utils.stopwatch;

import java.time.Duration;
import java.time.Instant;

public class Round {

    public String name;
    private Instant start, end;

    public Round(String name) {
        this.name = name;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public float toMillis() {
        if (start != null && end != null)
            return Duration.between(start, end).toMillis();
        else
            return -1;
    }
}
