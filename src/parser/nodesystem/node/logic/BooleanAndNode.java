package parser.nodesystem.node.logic;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.logic.conditions.ConditionNode;

/**
 * Returns 1 if both the booleans are true
 */
public class BooleanAndNode extends ConditionNode {

    public BooleanAndNode() {
        super(NodeType.BOOLEAN_AND_NODE);
    }

    @Override
    public Data run() {
        boolean val = left.run().asBoolean() == true && right.run().asBoolean() == true;

        if (val)
            return new Data(1, DataType.BOOLEAN);
        else
            return new Data(0, DataType.BOOLEAN);
    }

    @Override
    public void print() {
        super.print();
    }
}
