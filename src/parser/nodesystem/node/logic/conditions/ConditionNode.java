package parser.nodesystem.node.logic.conditions;

import parser.nodesystem.DataType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

//TODO
//? In LogicNode umbenennen? 
public abstract class ConditionNode extends Node {

    public Node left;
    public Node right;

    public ConditionNode(NodeType nodeType) {
        super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.BOOLEAN, nodeType);
    }
}
