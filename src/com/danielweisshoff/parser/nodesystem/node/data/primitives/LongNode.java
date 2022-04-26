package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;

public class LongNode extends PrimitiveNode {

	public LongNode(long value) {
		super(NodeType.LONG_NODE);
		data = new Data(value, DataType.LONG);
	}
}
