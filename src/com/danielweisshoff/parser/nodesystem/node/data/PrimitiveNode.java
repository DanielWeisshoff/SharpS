package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class PrimitiveNode extends NumberNode {

	public Data data;

	public PrimitiveNode(NodeType nodeType) {
		super(null, null, nodeType);
	}

	public Data getData() {
		return data;
	}

	@Override
	public Data run() {
		return data;
	}
}
