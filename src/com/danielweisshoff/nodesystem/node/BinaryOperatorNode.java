package com.danielweisshoff.nodesystem.node;

import com.danielweisshoff.nodesystem.BinaryOperator;
import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;


public class BinaryOperatorNode extends Node {

    private Data<Double> result;

    private final Node leftNode;
    private final BinaryOperator operator;
    private final Node rightNode;

    public BinaryOperatorNode(Node leftNode, BinaryOperator operator, Node rightNode) {
        super(new DataType[]{DataType.DOUBLE, DataType.DOUBLE}, DataType.DOUBLE);
        this.leftNode = leftNode;
        this.operator = operator;
        this.rightNode = rightNode;
    }

    @Override
    public Data<Double> execute() {
        if (result == null)
            calculateResult();
        return result;
    }

    private void calculateResult() {
        double left = leftNode.execute().toDouble();
        double right = rightNode.execute().toDouble();
        switch (operator) {
            case ADD:
                result = new Data<Double>(left + right, DataType.DOUBLE);
            case SUB:
                result = new Data<Double>(left - right, DataType.DOUBLE);
            case MUL:
                result = new Data<Double>(left * right, DataType.DOUBLE);
            case DIV:
                result = new Data<Double>(left / right, DataType.DOUBLE);
        }
    }
}
