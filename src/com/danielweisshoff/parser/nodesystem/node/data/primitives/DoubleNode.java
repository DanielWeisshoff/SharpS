package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;

public class DoubleNode extends PrimitiveNode {

	public DoubleNode(double value) {
		data = new Data<Double>(value, DataType.DOUBLE);
	}
}
