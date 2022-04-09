package com.danielweisshoff.parser.nodesystem.node.data.primitives;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;

public class FloatNode extends PrimitiveNode {

	public FloatNode(float value) {
		data = new Data<Float>(value, DataType.FLOAT);
	}
}
