package com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts;

import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;

public class PreIncrementNode extends AssignNode {

	public PreIncrementNode(String name) {
		super(name, NodeType.PRE_INCREMENT_NODE);
	}
}
