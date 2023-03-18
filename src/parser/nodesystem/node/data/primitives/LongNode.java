package parser.nodesystem.node.data.primitives;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class LongNode extends PrimitiveNode {

    public LongNode() {
        this(0);
    }

    public LongNode(long value) {
        super(new Data(value, DataType.LONG), NodeType.LONG_NODE);
    }

    @Override
    public void print() {
        super.print();
    }
}
