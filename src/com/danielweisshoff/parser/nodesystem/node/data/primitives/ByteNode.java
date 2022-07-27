package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

public class ByteNode extends PrimitiveNode {

	public ByteNode(byte value) {
		super(NodeType.BYTE_NODE);
		data = new Data(value, DataType.BYTE);
	}

}
