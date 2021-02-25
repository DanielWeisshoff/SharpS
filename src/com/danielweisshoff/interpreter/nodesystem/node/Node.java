package com.danielweisshoff.interpreter.nodesystem.node;


import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;

/**
 * The power of creation lies at our feet.
 * The only thing holding you back is <i>imagination</i>
 */
public class Node {

    /*//Spezifiziert, welche/r Datentyp angenommen / ausgegeben wird
    private final DataType[] inputType;
    private final DataType outputType;*/

    public Node(DataType[] inputType, DataType outputType) {
        // this.inputType = inputType;
        // this.outputType = outputType;
    }

    public Data<?> execute() {
        return new Data<>(1, DataType.INT);
    }
}
