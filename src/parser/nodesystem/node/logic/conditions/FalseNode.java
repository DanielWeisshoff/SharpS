package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.NodeType;

public class FalseNode extends ConditionNode {

    public FalseNode() {
        super(NodeType.FALSE_NODE);
    }

    @Override
    public Data run() {
        return new Bool(false);
    }

    @Override
    public void print() {
        super.print();
    }
}
