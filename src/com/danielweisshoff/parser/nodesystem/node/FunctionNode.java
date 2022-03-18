package com.danielweisshoff.parser.nodesystem.node;

import java.util.ArrayList;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/**
 * Forms an entry point for the program
 * Executes all child nodes like a queue
 */
public class FunctionNode extends Node {

	private final ArrayList<Node> children;
	private final String name;

	public FunctionNode(String name) {
		super(null, DataType.INT);
		children = new ArrayList<>();
		this.name = name;
	}

	public void add(Node n) {
		children.add(n);
	}

	public String getName() {
		return name;
	}

	@Override
	public Data<Integer> execute() {
		for (Node n : children) {
			n.execute();
		}
		return new Data<>(1, DataType.INT); //TODO return variabel
	}
}