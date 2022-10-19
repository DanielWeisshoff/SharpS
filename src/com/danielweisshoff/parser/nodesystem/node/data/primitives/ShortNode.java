package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class ShortNode extends PrimitiveNode {

    public ShortNode() {
        this((short) 0);
    }

    public ShortNode(short value) {
        super(NodeType.SHORT_NODE);
        data = new Data(value, DataType.SHORT);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asShort(), depth + 1);
    }
}
