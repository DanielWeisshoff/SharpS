package com.danielweisshoff.parser.nodesystem.node.binaryoperations;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

/**
 * Divides two values and returns the result
 */
public abstract class BinaryOperationNode extends Node {

	public Node left;
	public Node right;
	protected Data<Double> result;

	public BinaryOperationNode(DataType[] inputType, DataType outputType) {
		super(inputType, outputType);
	}

	@Override
	public Data<Double> execute() {
		if (result == null)
			calculateResult();
		return result;
	}

	protected abstract void calculateResult();

}