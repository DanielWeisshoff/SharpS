package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

public class CallNode extends Node {
    private final String name;

    public CallNode(String name) {
        super(null, null);
        this.name = name;
    }

    @Override
    public Data<?> execute() {
        Data<?> data = new Data<>(null, DataType.NULL);

        if (BuiltInFunction.builtInFunctions.containsKey(name)) {
            data = BuiltInFunction.builtInFunctions.get(name).call();
            System.out.println("BuiltInFunktion " + name + " wird aufgerufen");
        } else if (Parser.methods.containsKey(name)) {
            data = Parser.methods.get(name).getNode().execute();
            System.out.println("Funktion " + name + " wird aufgerufen");
        }

        return data;
    }
}
