package parser.nodesystem.node.data.primitives;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

public class FloatNode extends PrimitiveNode {

    public FloatNode() {
        this(0);
    }

    public FloatNode(float value) {
        super(new Data(value, DataType.FLOAT), NodeType.FLOAT_NODE);
    }

    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced("" + data.asFloat(), depth + 1);
    }
}
