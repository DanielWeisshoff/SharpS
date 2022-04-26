package com.danielweisshoff.parser.nodesystem.node.logic;

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
}
