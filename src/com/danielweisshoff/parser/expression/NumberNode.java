package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.nodesystem.node.Node;

class NumberNode extends ExpressionNode {

    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public double execute() {
        return value;
    }

    @Override
    public Node toNode() {
        return new com.danielweisshoff.interpreter.nodesystem.node.data.NumberNode(value);
    }
}
