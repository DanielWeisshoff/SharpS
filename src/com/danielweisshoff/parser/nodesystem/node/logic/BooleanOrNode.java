package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Returns 1 if at least one of the booleans is true
 */
public class BooleanOrNode extends ConditionNode {

	public BooleanOrNode() {
		super(NodeType.BOOLEAN_OR_NODE);
	}
}
