package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.IntegerNode;

/**
 * Initializes a variable
 */
public class InitNode extends Node {
	private final String name;
	public final Node expression;

	public InitNode(String name, Node expression) {
		super(null, null);
		this.name = name;
		this.expression = expression;
	}

	public InitNode(String name) {
		super(null, null);
		this.name = name;
		this.expression = new IntegerNode(0);
	}

	@Override
	public Data<?> execute() {
		return new Data<>(1, DataType.INT);
	}

	public String getName() {
		return name;
	}
}
