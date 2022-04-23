package com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts;

import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;

public class PostDecrementNode extends AssignNode {

	public PostDecrementNode(String name) {
		super(name, NodeType.POST_DECREMENT_NODE);
	}
}
