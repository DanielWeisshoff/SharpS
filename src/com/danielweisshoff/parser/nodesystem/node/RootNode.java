package com.danielweisshoff.parser.nodesystem.node;

import java.util.ArrayList;

import com.danielweisshoff.parser.nodesystem.DataType;

public class RootNode extends Node {

	private ArrayList<ClassNode> classes;

	public RootNode() {
		super(null, DataType.INT);
		classes = new ArrayList<>();
	}

	public void addClass(ClassNode n) {
		classes.add(n);
	}

	public ArrayList<ClassNode> getClasses() {
		return classes;
	}

}
