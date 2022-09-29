package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class LongNode extends PrimitiveNode {

    public LongNode(long value) {
        super(NodeType.LONG_NODE);
        data = new Data(value, DataType.LONG);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asLong(), depth + 1);
    }
}
