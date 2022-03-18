package com.danielweisshoff.parser.nodesystem.node.booleanoperations;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

/**
 * Returns 1 if at least one of the booleans is true
 */
public class BooleanOrNode extends Node {

	private final Node left;
	private final Node right;

	public BooleanOrNode(Node left, Node right) {
		super(null, null);
		this.left = left;
		this.right = right;
	}

	@Override
	public Data<?> execute() {
		byte l = left.execute().asByte();
		byte r = right.execute().asByte();
		return new Data<>(l == 1 || r == 1 ? 1 : 0, DataType.BOOLEAN);
	}
}
