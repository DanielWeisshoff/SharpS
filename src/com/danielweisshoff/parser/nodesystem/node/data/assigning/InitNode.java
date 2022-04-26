package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;

public class InitNode extends AssignNode {

	public InitNode(String name) {
		super(name, NodeType.INIT_NODE);
	}

}
