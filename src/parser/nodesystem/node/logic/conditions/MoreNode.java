package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class MoreNode extends ConditionNode {

    public MoreNode(Node left, Node right) {
        super(NodeType.MORE_NODE);

        this.left = left;
        this.right = right;
    }

    @Override
    public Data run() {
        boolean val = left.run().asDouble() > right.run().asDouble();

        if (val)
            return new Bool(true);
        else
            return new Bool(false);
    }

    @Override
    public void print() {
        super.print();

        left.print();
        right.print();
    }
}
