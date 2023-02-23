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

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        left.print(depth + 1);
        right.print(depth + 1);
    }
}