package com.danielweisshoff.parser;

import java.util.HashMap;

import com.danielweisshoff.parser.nodesystem.node.ClassNode;
import com.danielweisshoff.parser.nodesystem.node.RootNode;

public class AST {

	private RootNode root;

	public HashMap<String, ClassNode> classes;

	public AST(RootNode root) {
		this.root = root;

		//Just finding the nodes in the AST
		initClassesDictionary();
	}

	private void initClassesDictionary() {
		classes = new HashMap<>();

		for (ClassNode cn : root.getClasses())
			classes.put(cn.getName(), cn);
	}
}
