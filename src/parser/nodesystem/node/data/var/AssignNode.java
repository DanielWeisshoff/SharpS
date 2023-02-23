package parser.nodesystem.node.data.var;

import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.binaryoperations.NumberNode;

/**
 * Sets the value of a variable
 */
public abstract class AssignNode extends NumberNode {

    //TODO this is only used for incrementation/decrementation
    public VariableNode variable;

    public Node expression;
    protected final String name;

    public AssignNode(String name, NodeType nodeType) {
        super(null, null, nodeType);
        this.name = name;
    }
}