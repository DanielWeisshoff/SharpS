package com.danielweisshoff.interpreter.nodesystem.node;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;

/* TODO
 *  - Eventuell für Kommazahlen eine separate Node
 *  - Generell sollte es eine gute und adaptierbare Alternative geben,
 *    falls viele unterschiedliche Datentypen wie int,float,double,short etc... genutzt werden
 */

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
