package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class VariableEntry extends Entry {

    public NumberNode node;
    public DataType dataType;

    public VariableEntry(String name, long id, NumberNode node, DataType dataType) {
        super(name, id, Type.VARIABLE);
        this.node = node;
        this.dataType = dataType;
    }
}
