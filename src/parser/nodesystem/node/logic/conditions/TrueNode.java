package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.NodeType;

public class TrueNode extends ConditionNode {

    public TrueNode() {
        super(NodeType.TRUE_NODE);
    }

    @Override
    public Data run() {
        return new Bool(true);
    }

    @Override
    public void print() {
        super.print();
    }
}
