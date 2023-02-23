package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public abstract class PrimitiveNode extends NumberNode {

    public final Data data;

    public PrimitiveNode(Data data, NodeType nodeType) {
        super(null, null, nodeType);
        this.data = data;
    }

    @Override
    public Data run() {
        return data;
    }
}
