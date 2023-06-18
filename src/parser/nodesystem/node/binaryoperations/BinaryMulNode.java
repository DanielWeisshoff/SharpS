package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.NodeType;
import parser.parser.Arithmetic;

/**
 * Multiplies two values and returns the result
 */
public class BinaryMulNode extends BinaryOperationNode {

    public BinaryMulNode() {
        super(null, null, NodeType.BINARY_MUL_NODE);
    }

    @Override
    public Data run() {
        Numerical a = (Numerical) left.run();
        Numerical b = (Numerical) right.run();
        return Arithmetic.mul(a, b);
    }

}