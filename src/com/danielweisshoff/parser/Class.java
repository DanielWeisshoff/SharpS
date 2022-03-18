package com.danielweisshoff.parser;

import java.util.HashMap;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class Class {

	private String name;
	private final HashMap<String, Node> methods;
	private final HashMap<String, Data<?>> variables;

	public Class(String name) {
		this.name = name;
		methods = new HashMap<>();
		variables = new HashMap<>();
	}

	public Node getMethod(String name) {
		return methods.get(name);
	}

	public Data<?> getVariables(String name) {
		return variables.get(name);
	}

	public void addMethod(String name, Node n) {
		methods.put(name, n);
	}
}
