package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.node.NodeType;

/*
Saves the name of a vatiable and searches for its id at runtime
*/
public class PointerNode extends PrimitiveNode {

	public String variable;

	public PointerNode(String name) {
		super(NodeType.POINTER_NODE);

		this.variable = name;

	}
}
