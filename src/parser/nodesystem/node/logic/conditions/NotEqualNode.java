package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class NotEqualNode extends ConditionNode {

    public NotEqualNode(Node left, Node right) {
        super(NodeType.NOT_EQUAL_NODE);

        this.left = left;
        this.right = right;
    }

    @Override
    public Data run() {
        boolean val = left.run().asDouble() != right.run().asDouble();

        if (val)
            return new Data(1, DataType.BOOLEAN);
        else
            return new Data(0, DataType.BOOLEAN);
    }

    @Override
    public void print() {
        super.print();

        left.print();
        right.print();
    }
}
