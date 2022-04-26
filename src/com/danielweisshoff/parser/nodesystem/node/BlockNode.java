package com.danielweisshoff.parser.nodesystem.node;

import java.util.ArrayList;

import com.danielweisshoff.parser.nodesystem.DataType;

/**
 * Represents a code block.
 */
public class BlockNode extends Node {

	public ArrayList<Node> children;

	public BlockNode() {
		super(null, DataType.INT, NodeType.BLOCK_NODE);
		children = new ArrayList<>();
	}

	public void add(Node n) {
		children.add(n);
	}
}