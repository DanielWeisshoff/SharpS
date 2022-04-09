package com.danielweisshoff.parser.nodesystem.node.data.numbers;

import com.danielweisshoff.parser.nodesystem.node.Node;

public class FloatingPointNumberNode extends Node {

	public double value;

	public FloatingPointNumberNode(double value) {
		super(null, null);
		this.value = value;
	}

}
