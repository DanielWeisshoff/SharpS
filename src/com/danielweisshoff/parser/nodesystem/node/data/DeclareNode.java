package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.IntegerNode;

/**
 * Initializes a variable
 */
public class DeclareNode extends Node {
	private final String name;
	public final DataType dataType;
	public final Node expression;

	public DeclareNode(String name, DataType dataType, Node expression) {
		super(null, null, NodeType.DECLARE_NODE);
		this.name = name;
		this.dataType = dataType;
		this.expression = expression;
	}

	public DeclareNode(String name, DataType dataType) {
		super(null, null, NodeType.DECLARE_NODE);

		this.name = name;
		this.dataType = dataType;
		this.expression = new IntegerNode(0);
	}

	public String getName() {
		return name;
	}
}
