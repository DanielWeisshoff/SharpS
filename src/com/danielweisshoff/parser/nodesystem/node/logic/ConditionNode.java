package com.danielweisshoff.parser.nodesystem.node.logic;

import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

public abstract class ConditionNode extends Node {

	public ConditionNode() {
		super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.BOOLEAN);
	}
}