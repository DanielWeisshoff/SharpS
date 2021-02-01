package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.container.Variable;
import com.danielweisshoff.parser.nodesystem.Data;

public class AssignNode extends Node {

    private Node variable;
    private Node expression;

    public AssignNode(Node variable, Node expression) {
        super(null, null);
    }

    @Override
    public Data<?> execute() {
        String varName = variable.execute().toString();
        Data<?> data = expression.execute();
        new Variable(varName, data);
        return null;
    }
}
