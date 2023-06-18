package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.NodeType;
import parser.parser.Arithmetic;

/**
 * Subtracts two values and returns the result
 */
public class BinarySubNode extends BinaryOperationNode {

    public BinarySubNode() {
        super(null, null, NodeType.BINARY_SUB_NODE);
    }

    @Override
    public Data run() {
        Numerical a = (Numerical) left.run();
        Numerical b = (Numerical) right.run();
        return Arithmetic.sub(a, b);
    }
}