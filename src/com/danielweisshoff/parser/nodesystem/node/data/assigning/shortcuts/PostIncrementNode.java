package com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts;

import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;

public class PostIncrementNode extends AssignNode {

	public PostIncrementNode(String name) {
		super(name, NodeType.POST_INCREMENT_NODE);
	}
}
