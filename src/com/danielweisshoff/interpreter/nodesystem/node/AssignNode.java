package com.danielweisshoff.interpreter.nodesystem.node;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.parser.container.Variable;

/**
 * Sets the value of a variable
 */
public class AssignNode extends Node {

    private final VariableNode variable;
    private final Node expression;

    public AssignNode(VariableNode variable, Node expression) {
        super(null, null);
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public Data<?> execute() {
        String varName = variable.execute().toString();
        Data<?> data = expression.execute();
        new Variable(varName, data);
        return new Data<>(1, DataType.INT);
    }
}