package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.nodesystem.node.Node;

abstract class ExpressionNode {
    public abstract double execute();

    public abstract Node toNode();
}
