package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class IfNode extends Node {

	public ConditionNode condition;
	public BlockNode condBlock, elseBlock;

	public IfNode() {
		super(new DataType[] {}, DataType.BOOLEAN);
		condBlock = new BlockNode();
	}

	@Override
	public Data<?> execute() {
		if (condition.execute().asInt() == 1)
			condBlock.execute();
		else if (elseBlock != null)
			elseBlock.execute();

		return new Data<>();
	}

}
