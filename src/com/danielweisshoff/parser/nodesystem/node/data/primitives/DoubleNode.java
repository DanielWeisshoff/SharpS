package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class DoubleNode extends PrimitiveNode {

	public DoubleNode(double value) {
		super(NodeType.DOUBLE_NODE);
		data = new Data(value, DataType.DOUBLE);
	}
}
