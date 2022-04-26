package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Compares the given Nodes
 * returns 1 or 0
 */
public class LessNode extends ConditionNode {

	public LessNode(Node left, Node right) {
		super(NodeType.LESS_NODE);

		this.left = left;
		this.right = right;
	}
}
