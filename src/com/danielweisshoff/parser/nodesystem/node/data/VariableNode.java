package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 */

/**
 * Holds the name of a variable and returns the stored data
 */
public class VariableNode extends Node {

	private final String name;

	public VariableNode(String name) {
		super(null, null, NodeType.VARIABLE_NODE);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public Data<?> execute() {
		return new Data<>();
	}
}
