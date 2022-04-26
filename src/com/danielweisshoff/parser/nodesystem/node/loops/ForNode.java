package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class ForNode extends Node {

	public DeclareNode init;
	public ConditionNode condition;
	public AssignNode assignment;
	public BlockNode block;

	public ForNode() {
		super(null, null, NodeType.FOR_NODE);
	}

}
