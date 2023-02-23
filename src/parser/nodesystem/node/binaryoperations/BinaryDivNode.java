package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

/**
 * Divides two values and returns the result
 */
public class BinaryDivNode extends BinaryOperationNode {

    public BinaryDivNode() {
        super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.DOUBLE, NodeType.BINARY_DIV_NODE);
    }

    @Override
    public Data run() {
        double val = left.run().asDouble() / right.run().asDouble();
        return new Data(val, DataType.DOUBLE);
    }
}