package com.danielweisshoff.interpreter.nodesystem.node.data;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.parser.Parser;

/**
 * Initializes a variable
 */
public class InitNode extends Node {
    private final String name;
    private final Node expression;

    public InitNode(String name, Node expression) {
        super(null, null);
        this.name = name;
        this.expression = expression;
    }

    public InitNode(String name) {
        super(null, null);
        this.name = name;
        this.expression = new NumberNode(0);
    }

    @Override
    public Data<?> execute() {
        Parser.variables.put(name, expression.execute());
        return new Data<>(1, DataType.INT);
    }
}
