package com.danielweisshoff.parser.nodesystem.node;


import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

import java.util.ArrayList;

public class EntryNode extends Node {

    private final ArrayList<Node> children;
    private final String name;

    public EntryNode(String name) {
        super(new DataType[]{DataType.ANY}, DataType.INT);
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
            n.execute().print();
        }
        return new Data<>(1, DataType.INT);
    }
}