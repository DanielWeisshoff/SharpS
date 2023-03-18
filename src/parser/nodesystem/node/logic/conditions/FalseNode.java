package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class FalseNode extends ConditionNode {

    public FalseNode() {
        super(NodeType.FALSE_NODE);
    }

    @Override
    public Data run() {
        return new Data(0, DataType.BOOLEAN);
    }

    @Override
    public void print() {
        super.print();
    }
}
