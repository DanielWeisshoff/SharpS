package com.danielweisshoff.interpreter.nodesystem.node.binaryoperations;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.interpreter.nodesystem.node.Node;

/**
 * Adds two values and returns the result
 */
public class BinaryAddNode extends Node {

    private final Node left;
    private final Node right;
    private Data<Double> result;

    public BinaryAddNode(Node leftNode, Node rightNode) {
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
        double leftResult = left.execute().asDouble();
        double rightResult = right.execute().asDouble();

        result = new Data<Double>(leftResult + rightResult, DataType.DOUBLE);
    }
}