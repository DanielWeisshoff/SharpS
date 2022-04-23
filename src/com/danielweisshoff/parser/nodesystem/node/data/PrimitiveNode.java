package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class PrimitiveNode extends Node {

	public Data<?> data;

	public PrimitiveNode(NodeType nodeType) {
		super(null, null, nodeType);
	}

	public Data<?> getData() {
		return data;
	}
}
