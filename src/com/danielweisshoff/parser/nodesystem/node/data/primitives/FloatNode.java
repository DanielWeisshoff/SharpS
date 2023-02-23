package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class FloatNode extends PrimitiveNode {

    public FloatNode() {
        this(0);
    }

    public FloatNode(float value) {
        super(new Data(value, DataType.FLOAT), NodeType.FLOAT_NODE);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asFloat(), depth + 1);
    }
}
