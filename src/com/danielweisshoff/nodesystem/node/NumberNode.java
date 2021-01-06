package com.danielweisshoff.nodesystem.node;

import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;
import com.danielweisshoff.nodesystem.Node;

public class NumberNode extends Node {

    private final Data<Double> value;

    public NumberNode(double value) {
        super(new DataType[]{DataType.DOUBLE}, DataType.DOUBLE);
        this.value = new Data<Double>(value, DataType.DOUBLE);
    }

    public Data<Double> getValue() {
        return value;
    }

    @Override
    public Data<Double> execute() {
        return value;
    }
}
