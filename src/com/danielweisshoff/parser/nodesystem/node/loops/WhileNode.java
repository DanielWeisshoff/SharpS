package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class WhileNode extends Node {

	public ConditionNode condition;
	public BlockNode whileBlock;

	public WhileNode() {
		super(null, null, NodeType.WHILE_NODE);
	}

}
