package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.NodeType;
import parser.parser.Arithmetic;

public class BinaryModNode extends BinaryOperationNode {

    public BinaryModNode() {
        super(null, null, NodeType.BINARY_MOD_NODE);

    }

    @Override
    public Data run() {
        Numerical a = (Numerical) left.run();
        Numerical b = (Numerical) right.run();
        return Arithmetic.mod(a, b);
    }
}
