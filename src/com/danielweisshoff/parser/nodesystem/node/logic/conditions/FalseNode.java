package com.danielweisshoff.parser.nodesystem.node.logic.conditions;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class FalseNode extends ConditionNode {

	public FalseNode() {
		super(NodeType.FALSE_NODE);
	}

	@Override
	public Data run() {
		return new Data(0, DataType.BOOLEAN);
	}

}
