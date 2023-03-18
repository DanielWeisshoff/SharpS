package interpreter.builtin.functions;

import java.util.HashMap;

import parser.nodesystem.Data;

public abstract class BuiltInFunction {

    public static HashMap<String, BuiltInFunction> builtInFunctions = new HashMap<>();

    static {
        builtInFunctions.put("help", new helpFunction());
        builtInFunctions.put("exit", new exitFunction());
    }

    public abstract Data call();
}
