package com.danielweisshoff.nodesystem;

public class NumberNode extends Node {

    private final Data<Double> value;

    public NumberNode(double value) {
        super(new DataType[]{DataType.DOUBLE}, DataType.DOUBLE);
        this.value = new Data<Double>(value, DataType.DOUBLE);
    }

    @Override
    public Data<Double> execute() {
        return value;
    }
}
