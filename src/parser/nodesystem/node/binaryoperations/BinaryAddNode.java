package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.NodeType;
import parser.parser.Arithmetic;

/**
 * Adds two values and returns the result
 */
public class BinaryAddNode extends BinaryOperationNode {

    public BinaryAddNode() {
        super(null, null, NodeType.BINARY_ADD_NODE);
    }

    @Override
    public Data run() {
        Numerical a = (Numerical) left.run();
        Numerical b = (Numerical) right.run();
        return Arithmetic.add(a, b);
    }
}