package com.danielweisshoff.parser.nodesystem.node;

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
        if (Parser.methods.containsKey(name)) {
            data = Parser.methods.get(name).getNode().execute();
        }
        return data;
    }
}
