package parser.nodesystem.node.data.primitives;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

//TODO? eig. unnütz
/**representative for all Nodes that save or calculate a numeric value*/
public class NumberNode extends Node {

    private Numerical value;

    public NumberNode(Numerical value, Data[] inputType, Data outputType, NodeType nodeType) {
        super(inputType, outputType, nodeType);
        this.value = value;
    }

    @Override
    public void print() {
        //TODO
    }

    @Override
    public Data run() {
        return value;
    }
}
