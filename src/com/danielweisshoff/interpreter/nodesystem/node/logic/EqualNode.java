package com.danielweisshoff.interpreter.nodesystem.node.logic;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.interpreter.nodesystem.node.Node;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class EqualNode extends Node {

    private final Node left;
    private final Node right;

    public EqualNode(Node left, Node right) {
        super(new DataType[]{DataType.ANY}, DataType.BOOL);
        this.left = left;
        this.right = right;
    }

    @Override
    public Data<Integer> execute() {
        double a = left.execute().getData().doubleValue();
        double b = right.execute().getData().doubleValue();
        boolean bool = a == b;

        if (bool)
            return new Data<>(1, DataType.INT);
        return new Data<>(0, DataType.INT);
    }
}
