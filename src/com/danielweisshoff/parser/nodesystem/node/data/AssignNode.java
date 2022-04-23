package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Sets the value of a variable
 */
public abstract class AssignNode extends Node {

	public VariableNode variable;
	public Node expression;
	private final String name;

	public AssignNode(String name, NodeType nodeType) {
		super(null, null, nodeType);
		this.name = name;
	}

	@Override
	public Data<?> execute() {
		return new Data<>(1, DataType.INT);
	}

	public String getName() {
		return name;
	}
}