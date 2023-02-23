package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class ByteNode extends PrimitiveNode {

    public ByteNode() {
        this((byte) 0);
    }

    public ByteNode(byte value) {
        super(new Data(value, DataType.BYTE), NodeType.BYTE_NODE);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asByte(), depth + 1);
    }
}
