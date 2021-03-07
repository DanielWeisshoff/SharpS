package com.danielweisshoff.interpreter.nodesystem.node;


import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;

import java.util.ArrayList;

/**
 * Forms an entry point for the program
 * Executes all child nodes like a queue
 */
public class EntryNode extends Node {

    private final ArrayList<Node> children;
    private final String name;

    public EntryNode(String name) {
        super(null, DataType.INT);
        children = new ArrayList<>();
        this.name = name;
    }

    public void add(Node n) {
        children.add(n);
    }

    public String getName() {
        return name;
    }

    @Override
    public Data<Integer> execute() {
        for (Node n : children) {
            n.execute();
        }
        return new Data<>(1, DataType.INT);
    }
}