package com.danielweisshoff.nodesystem.node;


import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;

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
