package com.danielweisshoff.interpreter.nodesystem.node;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;

/**
 * Divides two values and returns the result
 */
public class BinaryDivNode extends Node {

    private final Node left;
    private final Node right;
    private Data<Double> result;

    public BinaryDivNode(Node leftNode, Node rightNode) {
        super(new DataType[]{DataType.DOUBLE, DataType.DOUBLE}, DataType.DOUBLE);
        this.left = leftNode;
        this.right = rightNode;
    }

    @Override
    public Data<Double> execute() {
        if (result == null)
            calculateResult();
        System.out.println(result);
        return result;
    }

    private void calculateResult() {
        double leftResult = left.execute().toDouble();
        double rightResult = right.execute().toDouble();

        result = new Data<Double>(leftResult / rightResult, DataType.DOUBLE);
    }
}