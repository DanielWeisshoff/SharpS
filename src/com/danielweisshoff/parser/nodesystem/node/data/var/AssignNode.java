package com.danielweisshoff.parser.nodesystem.node.data.var;

import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

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