package com.danielweisshoff.parser.nodesystem.node.binaryoperations;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class BinaryModNode extends BinaryOperationNode {

	public BinaryModNode() {
		super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.DOUBLE, NodeType.BINARY_MOD_NODE);

	}

	@Override
	public Data run() {
		double val = left.run().asDouble() % right.run().asDouble();
		return new Data(val, DataType.DOUBLE);
	}
}
