package parser.nodesystem.node.data.primitives;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class ShortNode extends PrimitiveNode {

    public ShortNode() {
        this((short) 0);
    }

    public ShortNode(short value) {
        super(new Data(value, DataType.SHORT), NodeType.SHORT_NODE);
    }

    @Override
    public void print() {
        super.print();
    }
}
