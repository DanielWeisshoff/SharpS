package com.danielweisshoff.parser.nodesystem.node;


import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

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
