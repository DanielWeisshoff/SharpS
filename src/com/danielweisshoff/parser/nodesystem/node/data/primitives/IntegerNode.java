package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;

public class IntegerNode extends PrimitiveNode {

	public IntegerNode(int value) {
		super(NodeType.INTEGER_NODE);
		data = new Data(value, DataType.INT);
	}
}
