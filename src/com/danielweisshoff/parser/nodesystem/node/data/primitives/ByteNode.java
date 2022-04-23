package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;

public class ByteNode extends PrimitiveNode {

	public ByteNode(byte value) {
		data = new Data<Byte>(value, DataType.BYTE);
	}

}