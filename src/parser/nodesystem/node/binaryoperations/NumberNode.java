package parser.nodesystem.node.binaryoperations;

import parser.nodesystem.DataType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

//TODO? eig. unnütz
/**
 * representative for all Nodes that save or calculate a numeric value
 */
public abstract class NumberNode extends Node {

    public NumberNode(DataType[] inputType, DataType outputType, NodeType nodeType) {
        super(inputType, outputType, nodeType);
    }
}
