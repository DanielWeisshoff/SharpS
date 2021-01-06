package com.danielweisshoff.nodesystem.node;


import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;

import java.util.ArrayList;

public class EntryNode extends Node {

    private final ArrayList<Node> children;

    public EntryNode() {
        super(new DataType[]{DataType.ANY}, DataType.INT);
        children = new ArrayList<>();
    }

    public void add(Node n) {
        children.add(n);
    }

    @Override
    public Data<Integer> execute() {
        for (Node n : children) {
            n.execute().print();
        }
        return new Data<Integer>(1, DataType.INT);
    }
}

