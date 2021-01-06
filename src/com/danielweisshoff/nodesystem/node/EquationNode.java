package com.danielweisshoff.nodesystem.node;

import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;

public class EquationNode extends Node {
    private final Node left;
    private final Node right;

    public EquationNode(Node left, Node right) {
        super(new DataType[]{DataType.ANY}, DataType.BOOL);
        this.left = left;
        this.right = right;
    }

    @Override
    public Data<Integer> execute() {
        if (left.execute().getData().intValue() == right.execute().getData().intValue())
            return new Data<Integer>(1, DataType.INT);
        else
            return new Data<Integer>(0, DataType.INT);
    }
}
