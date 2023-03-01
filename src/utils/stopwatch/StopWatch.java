package utils.stopwatch;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

/**basic stopwatch for benchmarks**/
public class StopWatch {

    private ArrayList<Round> stoppedRounds = new ArrayList<>();
    private HashMap<String, Round> runningRounds = new HashMap<>();

    public void start(String name) {
        if (runningRounds.get(name) == null) {
            Round round = new Round(name);
            runningRounds.put(name, round);
            round.setStart(Instant.now());
        } else {
            System.out.println("Round with the name '" + name + "' already exists.");
            return;
        }
    }

    public void stop(String name) {
        if (runningRounds.get(name) != null) {
            Round round = runningRounds.get(name);
            round.setEnd(Instant.now());
            stoppedRounds.add(round);
        } else
            System.out.println("No round called '" + name + "' is running.");
    }

    public void printRounds() {
        for (Round r : stoppedRounds)
            System.out.println("[" + r.name + "] took " + r.toMillis() + "ms");
    }

    public Round getRound(String name) {
        return runningRounds.get(name);
    }
}
