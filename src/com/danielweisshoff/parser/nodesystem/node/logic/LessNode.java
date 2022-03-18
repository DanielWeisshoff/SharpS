package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class LessNode extends Node {

	private final Node left;
	private final Node right;

	public LessNode(Node left, Node right) {
		super(null, DataType.BOOLEAN);
		this.left = left;
		this.right = right;
	}

	@Override
	public Data<Integer> execute() {
		double a = left.execute().getData().doubleValue();
		double b = right.execute().getData().doubleValue();
		boolean bool = a < b;

		if (bool)
			return new Data<>(1, DataType.INT);
		return new Data<>(0, DataType.INT);
	}
}
