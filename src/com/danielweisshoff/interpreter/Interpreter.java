package com.danielweisshoff.interpreter;

import com.danielweisshoff.nodesystem.node.EntryNode;


public class Interpreter {

    private final EntryNode root;

    public Interpreter(EntryNode root) {
        this.root = root;
    }

    public void run() {
        root.execute().print();
    }
}
