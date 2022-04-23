package com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts;

import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;

public class PreDecrementNode extends AssignNode {

	public PreDecrementNode(String name) {
		super(name, NodeType.PRE_DECREMENT_NODE);

	}
}
