package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

/**
 * Returns 1 if at least one of the booleans is true
 */
public class BooleanOrNode extends ConditionNode {

	public BooleanOrNode() {
		super(NodeType.BOOLEAN_OR_NODE);
	}

	@Override
	public Data run() {
		boolean val = left.run().asBoolean() == true || right.run().asBoolean() == true;

		if (val)
			return new Data(1, DataType.BOOLEAN);
		else
			return new Data(0, DataType.BOOLEAN);
	}
}
