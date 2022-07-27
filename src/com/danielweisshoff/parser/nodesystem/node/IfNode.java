package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

public class IfNode extends Node {

	public ConditionNode condition;
	public BlockNode condBlock, elseBlock;

	public IfNode(ConditionNode condition) {
		super(new DataType[] {}, DataType.BOOLEAN, NodeType.IF_NODE);
		this.condition = condition;

		//TODO ?
		condBlock = new BlockNode();
	}

	@Override
	public Data run() {

		if (condition.run().asBoolean())
			condBlock.run();
		else if (elseBlock != null)
			elseBlock.run();

		//TODO empty
		return new Data();
	}
}
