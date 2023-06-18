package parser.nodesystem.node.logic;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
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
            return new Bool(true);
        else
            return new Bool(false);
    }

    @Override
    public void print() {
        super.print();
    }
}
