package com.danielweisshoff.interpreter.nodesystem.node;


import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;

public class Node {

    /*//Spezifiziert, welche/r Datentyp angenommen / ausgegeben wird
    private final DataType[] inputType;
    private final DataType outputType;*/

    public Node(DataType[] inputType, DataType outputType) {
        // this.inputType = inputType;
        // this.outputType = outputType;
    }

    public Data<?> execute() {
        return new Data<Integer>(1, DataType.INT);
    }
}
