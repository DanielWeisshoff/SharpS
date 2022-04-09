package com.danielweisshoff.parser.nodesystem.node.data.numbers;

import com.danielweisshoff.parser.nodesystem.node.Node;

public class IntegerNumberNode extends Node {

	public long value;

	public IntegerNumberNode(long value) {
		super(null, null);
		this.value = value;
	}
}
