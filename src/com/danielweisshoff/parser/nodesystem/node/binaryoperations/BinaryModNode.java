package com.danielweisshoff.parser.nodesystem.node.binaryoperations;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

public class BinaryModNode extends BinaryOperationNode {

	public BinaryModNode() {
		super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.DOUBLE);

	}

	@Override
	protected void calculateResult() {
		double leftResult = left.execute().asDouble();
		double rightResult = right.execute().asDouble();

		result = new Data<Double>(leftResult % rightResult, DataType.DOUBLE);
	}
}
