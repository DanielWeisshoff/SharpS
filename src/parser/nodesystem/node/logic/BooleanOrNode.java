package parser.nodesystem.node.logic;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.logic.conditions.ConditionNode;

/**
 * Returns 1 if at least one of the booleans is true
 */
public class BooleanOrNode extends ConditionNode {

    public BooleanOrNode() {
        super(NodeType.BOOLEAN_OR_NODE);
    }

    @Override
    public Data run() {
        boolean val = left.run().asBoolean() == true || right.run().asBoolean() == true;

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
