package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.InitNode;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class ForNode extends Node {

	public InitNode init;
	public ConditionNode condition;
	public AssignNode assignment;
	public BlockNode block;

	public ForNode() {
		super(null, null);
	}

}
