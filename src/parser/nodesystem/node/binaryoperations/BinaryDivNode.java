package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.NodeType;
import parser.parser.Arithmetic;

/**
 * Divides two values and returns the result
 */
public class BinaryDivNode extends BinaryOperationNode {

    public BinaryDivNode() {
        super(null, null, NodeType.BINARY_DIV_NODE);
    }

    @Override
    public Data run() {
        Numerical a = (Numerical) left.run();
        Numerical b = (Numerical) right.run();
        return Arithmetic.div(a, b);
    }
}