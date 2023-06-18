package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.primitives.NumberNode;

//TODO? sollte es von NumberNode erben?
/**
 * Divides two values and returns the result
 */
public abstract class BinaryOperationNode extends NumberNode {

    public NumberNode left;
    public NumberNode right;
    protected Numerical result;

    public BinaryOperationNode(Data[] inputType, Data outputType, NodeType nodeType) {
        super(null, inputType, outputType, nodeType);
    }

    @Override
    public void print() {
        super.print();
        left.print();
        right.print();
    }
}