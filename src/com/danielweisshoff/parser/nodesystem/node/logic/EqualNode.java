package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class EqualNode extends ConditionNode {

	public EqualNode(Node left, Node right) {
		super(NodeType.EQUAL_NODE);

		this.left = left;
		this.right = right;
	}

	@Override
	public Data<Integer> execute() {
		double a = left.execute().getData().doubleValue();
		double b = right.execute().getData().doubleValue();
		boolean bool = a == b;

		if (bool)
			return new Data<>(1, DataType.INT);
		return new Data<>(0, DataType.INT);
	}
}
