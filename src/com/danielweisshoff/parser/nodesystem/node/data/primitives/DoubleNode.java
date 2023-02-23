package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class DoubleNode extends PrimitiveNode {

    public DoubleNode() {
        this(0);
    }

    public DoubleNode(double value) {
        super(new Data(value, DataType.DOUBLE), NodeType.DOUBLE_NODE);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asDouble(), depth + 1);
    }
}
