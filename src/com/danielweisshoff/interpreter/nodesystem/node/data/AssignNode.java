package com.danielweisshoff.interpreter.nodesystem.node.data;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.parser.Parser;

/**
 * Sets the value of a variable
 */
public class AssignNode extends Node {

    private final String varName;
    private final Node expression;

    public AssignNode(String varName, Node expression) {
        super(null, null);
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public Data<?> execute() {
        Parser.variables.put(varName, expression.execute());
        return new Data<>(1, DataType.INT);
    }
}