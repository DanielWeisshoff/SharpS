package com.danielweisshoff.interpreter.builtin;

import com.danielweisshoff.interpreter.builtin.functions.helpFunction;
import com.danielweisshoff.interpreter.builtin.functions.printFunction;
import com.danielweisshoff.interpreter.nodesystem.Data;

import java.util.HashMap;

public abstract class BuiltInFunction {

    public static HashMap<String, BuiltInFunction> builtInFunctions = new HashMap<>();

    public static void registerAll() {
        builtInFunctions.put("print", new printFunction());
        builtInFunctions.put("help", new helpFunction());
    }

    public abstract Data<?> call();
}
