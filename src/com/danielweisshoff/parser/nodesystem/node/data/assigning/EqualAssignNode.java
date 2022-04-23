package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;

public class EqualAssignNode extends AssignNode {

	public EqualAssignNode(String name) {
		super(name, NodeType.EQUAL_ASSIGN_NODE);
	}

}
