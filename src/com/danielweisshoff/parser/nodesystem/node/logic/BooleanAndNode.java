package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Returns 1 if both the booleans are true
 */
public class BooleanAndNode extends ConditionNode {

	public BooleanAndNode() {
		super(NodeType.BOOLEAN_AND_NODE);
	}

	@Override
	public Data<?> execute() {
		byte l = left.execute().asByte();
		byte r = right.execute().asByte();
		return new Data<>(l == 1 && r == 1 ? 1 : 0, DataType.BOOLEAN);
	}
}
