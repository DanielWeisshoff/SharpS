package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

//TODO
//? In LogicNode umbenennen? 
public abstract class ConditionNode extends Node {

    public Node left;
    public Node right;

    public ConditionNode(NodeType nodeType) {
        super(null, null, nodeType);
    }
}
