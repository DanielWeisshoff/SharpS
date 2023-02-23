package parser;

public class IdRegistry {

    private static long variableID = 0;

    public static long newID() {
        return variableID++;
    }
}
