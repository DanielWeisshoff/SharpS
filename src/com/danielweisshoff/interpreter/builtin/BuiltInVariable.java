package com.danielweisshoff.interpreter.builtin;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.parser.container.Variable;

import java.util.HashMap;

public abstract class BuiltInVariable {

    public static HashMap<String, Variable> builtInVariables = new HashMap<>();

    public static void registerAll() {
        //Erstmals bleiben alle Builtin Variablen hier und werden global gespeichert
        //Später kann man sie ja in packages aufteilen (z.B pi -> Math.pi)
        builtInVariables.put("true", new Variable("true", new Data<>(1, DataType.INT)));
        builtInVariables.put("false", new Variable("false", new Data<>(0, DataType.INT)));
        builtInVariables.put("pi", new Variable("pi", new Data<>(3.14159265, DataType.DOUBLE)));
    }
}
