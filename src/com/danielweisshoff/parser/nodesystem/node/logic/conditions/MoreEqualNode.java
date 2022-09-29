package com.danielweisshoff.parser.nodesystem.node.logic.conditions;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class MoreEqualNode extends ConditionNode {

    public MoreEqualNode(Node left, Node right) {
        super(NodeType.MORE_EQUAL_NODE);

        this.left = left;
        this.right = right;
    }

    @Override
    public Data run() {
        boolean val = left.run().asDouble() >= right.run().asDouble();

        if (val)
            return new Data(1, DataType.BOOLEAN);
        else
            return new Data(0, DataType.BOOLEAN);
    }

    //TODO implementation 2.0
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);

        left.print(depth + 1);
        right.print(depth + 1);
    }
}
