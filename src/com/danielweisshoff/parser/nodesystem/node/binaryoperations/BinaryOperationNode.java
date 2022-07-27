package com.danielweisshoff.parser.nodesystem.node.binaryoperations;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * Divides two values and returns the result
 */
public abstract class BinaryOperationNode extends NumberNode {

	public NumberNode left;
	public NumberNode right;
	protected Data result;

	public BinaryOperationNode(DataType[] inputType, DataType outputType, NodeType nodeType) {
		super(inputType, outputType, nodeType);
	}
}