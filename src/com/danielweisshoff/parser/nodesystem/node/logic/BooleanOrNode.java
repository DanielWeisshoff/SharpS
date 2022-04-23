package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Returns 1 if at least one of the booleans is true
 */
public class BooleanOrNode extends ConditionNode {

	public BooleanOrNode() {
		super(NodeType.BOOLEAN_OR_NODE);
	}

	@Override
	public Data<?> execute() {
		byte l = left.execute().asByte();
		byte r = right.execute().asByte();
		return new Data<>(l == 1 || r == 1 ? 1 : 0, DataType.BOOLEAN);
	}
}
