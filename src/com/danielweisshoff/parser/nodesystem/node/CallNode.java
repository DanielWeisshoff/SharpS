package com.danielweisshoff.parser.nodesystem.node;

/**
 * Calls the defined function
 */
public class CallNode extends Node {
	private final String name;

	public CallNode(String name) {
		super(null, null, NodeType.CALL_NODE);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
