package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

/**
 * Sets the value of a variable
 */
public class AssignNode extends Node {

	private final String varName;
	private final Node expression;

	public AssignNode(String varName, Node expression) {
		super(null, null);
		this.varName = varName;
		this.expression = expression;
	}

	@Override
	public Data<?> execute() {
		// Parser.variables.put(varName, expression.execute());
		return new Data<>(1, DataType.INT);
	}
}