package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class IntegerNode extends PrimitiveNode {

    public IntegerNode() {
        this(0);
    }

    public IntegerNode(int value) {
        super(new Data(value, DataType.INT), NodeType.INTEGER_NODE);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asInt(), depth + 1);
    }
}
