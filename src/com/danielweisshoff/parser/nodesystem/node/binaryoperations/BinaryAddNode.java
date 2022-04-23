package com.danielweisshoff.parser.nodesystem.node.binaryoperations;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Adds two values and returns the result
 */
public class BinaryAddNode extends BinaryOperationNode {

	public BinaryAddNode() {
		super(new DataType[] { DataType.DOUBLE, DataType.DOUBLE }, DataType.DOUBLE, NodeType.BINARY_ADD_NODE);
	}

	@Override
	public Data<Double> execute() {
		if (result == null)
			calculateResult();
		return result;
	}

	protected void calculateResult() {
		double leftResult = left.execute().asDouble();
		double rightResult = right.execute().asDouble();

		result = new Data<Double>(leftResult + rightResult, DataType.DOUBLE);
	}
}