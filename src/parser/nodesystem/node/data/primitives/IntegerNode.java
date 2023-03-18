package parser.nodesystem.node.data.primitives;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class IntegerNode extends PrimitiveNode {

    public IntegerNode() {
        this(0);
    }

    public IntegerNode(int value) {
        super(new Data(value, DataType.INT), NodeType.INTEGER_NODE);
    }

    @Override
    public void print() {
        super.print();
    }
}
