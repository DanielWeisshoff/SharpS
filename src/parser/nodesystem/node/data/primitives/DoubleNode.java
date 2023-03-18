package parser.nodesystem.node.data.primitives;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class DoubleNode extends PrimitiveNode {

    public DoubleNode() {
        this(0);
    }

    public DoubleNode(double value) {
        super(new Data(value, DataType.DOUBLE), NodeType.DOUBLE_NODE);
    }

    @Override
    public void print() {
        super.print();
    }
}
