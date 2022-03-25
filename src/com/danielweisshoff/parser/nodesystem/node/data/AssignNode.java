package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

/**
 * Sets the value of a variable
 */
public class AssignNode extends Node {

	private final String name;
	public final Node expression;

	public AssignNode(String varName, Node expression) {
		super(null, null);
		this.name = varName;
		this.expression = expression;
	}

	@Override
	public Data<?> execute() {
		return new Data<>(1, DataType.INT);
	}

	public String getName() {
		return name;
	}
}