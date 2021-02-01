package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/* TODO
 * - In einzelne Nodes auslagern
 */

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class EquationNode extends Node {

    private final Node left;
    private final String compareType;
    private final Node right;


    public EquationNode(Node left, String compareType, Node right) {
        super(new DataType[]{DataType.ANY}, DataType.BOOL);
        this.left = left;
        this.compareType = compareType;
        this.right = right;
    }

    @Override
    public Data<Integer> execute() {
        double a = left.execute().getData().doubleValue();
        double b = right.execute().getData().doubleValue();
        boolean bool = false;
        switch (compareType) {
            case "<" -> bool = a < b;
            case "<=" -> bool = a <= b;
            case ">" -> bool = a > b;
            case ">=" -> bool = a >= b;
            case "==" -> bool = a == b;
            case "!=" -> bool = a != b;
        }
        if (bool)
            return new Data<>(1, DataType.INT);
        return new Data<>(0, DataType.INT);
    }
}
