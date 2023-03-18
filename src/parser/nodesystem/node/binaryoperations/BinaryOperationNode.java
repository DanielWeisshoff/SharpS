package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.NodeType;

/**
 * Divides two values and returns the result
 */
public abstract class BinaryOperationNode extends NumberNode {

    public NumberNode left;
    public NumberNode right;
    protected Data result;

    public BinaryOperationNode(DataType[] inputType, DataType outputType, NodeType nodeType) {
        super(inputType, outputType, nodeType);
    }

    @Override
    public void print() {
        super.print();
        left.print();
        right.print();
    }
}