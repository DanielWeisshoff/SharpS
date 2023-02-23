package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

/**
 * Adds two values and returns the result
 */
public class BinaryAddNode extends BinaryOperationNode {

    public BinaryAddNode() {
        super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.DOUBLE, NodeType.BINARY_ADD_NODE);
    }

    @Override
    public Data run() {
        double val = left.run().asDouble() + right.run().asDouble();
        return new Data(val, DataType.DOUBLE);
    }
}