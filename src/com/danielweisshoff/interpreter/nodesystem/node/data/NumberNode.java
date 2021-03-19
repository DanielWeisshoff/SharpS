package com.danielweisshoff.interpreter.nodesystem.node.data;


/* TODO
 *  - Eventuell für Kommazahlen eine separate Node
 *  - Generell sollte es eine gute und adaptierbare Alternative geben,
 *    falls viele unterschiedliche Datentypen wie int,float,double,short etc... genutzt werden
 */

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.interpreter.nodesystem.node.Node;

/**
 * Stores a normal or floating-point number
 */
public class NumberNode extends Node {

    private final Data<Double> value;

    public NumberNode(double value) {
        super(new DataType[]{DataType.DOUBLE}, DataType.DOUBLE);
        this.value = new Data<Double>(value, DataType.DOUBLE);
    }

    @Override
    public Data<?> execute() {
        return value;
    }
}
