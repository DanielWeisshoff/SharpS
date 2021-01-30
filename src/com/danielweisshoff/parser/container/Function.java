package com.danielweisshoff.parser.container;

import com.danielweisshoff.parser.nodesystem.node.EntryNode;

public class Function {

    private final EntryNode node;

    public Function(EntryNode node) {
        this.node = node;
    }

    public EntryNode getNode() {
        return node;
    }
}
