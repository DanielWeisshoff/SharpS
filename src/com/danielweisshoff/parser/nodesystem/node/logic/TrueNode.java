package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class TrueNode extends ConditionNode {

	public TrueNode() {
		super(NodeType.TRUE_NODE);
	}

	@Override
	public Data run() {
		return new Data(1, DataType.BOOLEAN);
	}

}
