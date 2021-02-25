package com.danielweisshoff.parser.container;

import com.danielweisshoff.interpreter.nodesystem.node.EntryNode;

public class Function {

    private final EntryNode node;

    public Function(EntryNode node) {
        this.node = node;
    }

    public EntryNode getNode() {
        return node;
    }
}
