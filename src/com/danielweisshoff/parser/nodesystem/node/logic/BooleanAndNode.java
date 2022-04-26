package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Returns 1 if both the booleans are true
 */
public class BooleanAndNode extends ConditionNode {

	public BooleanAndNode() {
		super(NodeType.BOOLEAN_AND_NODE);
	}
}
