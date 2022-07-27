package com.danielweisshoff.parser.nodesystem.node.binaryoperations;

import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/**
 * representative for all Nodes that save or calculate a numeric value
 */
public abstract class NumberNode extends Node {

	public NumberNode(DataType[] inputType, DataType outputType, NodeType nodeType) {
		super(inputType, outputType, nodeType);
	}
}
