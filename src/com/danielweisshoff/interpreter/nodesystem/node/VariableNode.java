package com.danielweisshoff.interpreter.nodesystem.node;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.parser.Parser;

public class VariableNode extends Node {

    private final String name;

    public VariableNode(String name) {
        super(null, null);
        this.name = name;
    }

    @Override
    public Data<?> execute() {
        Data<?> data = new Data<>(null, DataType.NULL);
        if (Parser.variables.containsKey(name)) {
            data = Parser.variables.get(name);
        }
        return data;
    }
}
