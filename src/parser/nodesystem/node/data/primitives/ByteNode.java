package parser.nodesystem.node.data.primitives;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class ByteNode extends PrimitiveNode {

    public ByteNode() {
        this((byte) 0);
    }

    public ByteNode(byte value) {
        super(new Data(value, DataType.BYTE), NodeType.BYTE_NODE);
    }

    @Override
    public void print() {
        super.print();
    }
}
