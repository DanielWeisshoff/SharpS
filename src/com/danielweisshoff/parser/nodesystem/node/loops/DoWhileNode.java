package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class DoWhileNode extends Node {

	public ConditionNode condition;
	public BlockNode whileBlock;

	public DoWhileNode() {
		super(null, null);
	}
}
