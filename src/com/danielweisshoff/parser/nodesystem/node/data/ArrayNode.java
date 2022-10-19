package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class ArrayNode extends NumberNode {

    private final String name;
    public final DataType dataType;
    public final NumberNode size;
    public Data[] fields;

    public ArrayNode(String name, DataType dataType, NumberNode size) {
        super(null, null, NodeType.ARRAY_NODE);
        this.name = name;
        this.dataType = dataType;
        this.size = size;
    }

    public Data getField(int index) {
        return fields[index];
    }

    //TODO should return the adress
    @Override
    public Data run() {
        return data;
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
