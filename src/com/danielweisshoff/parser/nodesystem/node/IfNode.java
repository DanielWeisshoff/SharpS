package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class IfNode extends Node {

	public ConditionNode condition;
	public BlockNode condBlock, elseBlock;

	public IfNode() {
		super(new DataType[] {}, DataType.BOOLEAN, NodeType.IF_NODE);
		condBlock = new BlockNode();
	}
}
