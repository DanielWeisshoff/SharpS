package com.danielweisshoff.interpreter.builtin;

import com.danielweisshoff.parser.nodesystem.Data;

import java.util.HashMap;

public abstract class BuiltInFunction {

    public static HashMap<String, BuiltInFunction> builtInFunctions = new HashMap<>();

    public abstract Data<?> call();

    public static void registerAll() {
        builtInFunctions.put("print", new printFunction());
        builtInFunctions.put("help", new helpFunction());
    }
}
