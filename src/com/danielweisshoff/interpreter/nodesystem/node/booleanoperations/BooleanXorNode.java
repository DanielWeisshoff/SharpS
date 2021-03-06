package com.danielweisshoff.interpreter.nodesystem.node.booleanoperations;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.interpreter.nodesystem.node.Node;

/**
 * Returns 1 if both booleans have a different value
 */
public class BooleanXorNode extends Node {

    private final Node left;
    private final Node right;

    public BooleanXorNode(Node left, Node right) {
        super(null, null);
        this.left = left;
        this.right = right;
    }

    @Override
    public Data<?> execute() {
        byte l = left.execute().toByte();
        byte r = right.execute().toByte();
        return new Data<>(l != r ? 1 : 0, DataType.BOOL);
    }
}
