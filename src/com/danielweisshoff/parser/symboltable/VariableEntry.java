package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class VariableEntry extends Entry {

    public NumberNode node;

    public VariableEntry(String name, long id, NumberNode node) {
        super(name, id, Type.VARIABLE);
        this.node = node;
    }
}
