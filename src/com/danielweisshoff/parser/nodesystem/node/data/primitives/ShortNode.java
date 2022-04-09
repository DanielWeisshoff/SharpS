package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;

public class ShortNode extends PrimitiveNode {

	public ShortNode(short value) {
		data = new Data<Short>(value, DataType.SHORT);
	}
}
