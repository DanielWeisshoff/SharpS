package com.danielweisshoff.parser.nodesystem.node;

import java.util.ArrayList;

public class ClassNode extends Node {

	private String name;
	private final ArrayList<Node> attributes;
	private final ArrayList<Node> functions;

	public ClassNode(String name) {
		super(null, null);
		this.name = name;
		attributes = new ArrayList<>();
		functions = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addAttribute(Node n) {
		attributes.add(n);
	}

	public void addFunction(Node n) {
		functions.add(n);
	}

	public ArrayList<Node> getFunctions() {
		return functions;
	}
}
