package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class PrimitiveNode extends Node {

	public Data<?> data;

	public PrimitiveNode() {
		super(null, null);
	}

	public Data<?> getData() {
		return data;
	}
}
