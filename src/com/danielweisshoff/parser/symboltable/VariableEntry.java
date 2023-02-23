package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.nodesystem.DataType;

public class VariableEntry extends Entry {

    public int fields;
    public DataType dataType;

    public VariableEntry(String name, long id, DataType dataType) {
        super(name, id, Type.VARIABLE);
        this.dataType = dataType;
        fields = 1;
    }

    public VariableEntry(String name, long id, int size, DataType dataType) {
        super(name, id, Type.VARIABLE);
        this.fields = size;
        this.dataType = dataType;

    }
}
