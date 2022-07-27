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
	public Data run() {
		boolean val = left.run().asBoolean() == true && right.run().asBoolean() == true;

		if (val)
			return new Data(1, DataType.BOOLEAN);
		else
			return new Data(0, DataType.BOOLEAN);
	}
}
