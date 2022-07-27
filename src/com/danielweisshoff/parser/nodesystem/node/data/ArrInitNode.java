package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class ArrInitNode extends Node {

	public final DataType type;
	public final NumberNode size;
	private Data[] fields;

	public ArrInitNode(DataType type, NumberNode size) {
		super(null, null, NodeType.ARRAY_NODE);
		this.type = type;
		this.size = size;
	}

	public Data getField(int index) {
		return fields[index];
	}

	@Override
	public Data run() {
		fields = new Data[size.run().asInt()];
		return new Data();
	}
}
