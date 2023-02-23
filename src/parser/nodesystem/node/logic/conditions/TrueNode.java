package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class TrueNode extends ConditionNode {

    public TrueNode() {
        super(NodeType.TRUE_NODE);
    }

    @Override
    public Data run() {
        return new Data(1, DataType.BOOLEAN);
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
    }
}
