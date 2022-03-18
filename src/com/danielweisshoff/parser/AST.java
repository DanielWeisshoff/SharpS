package com.danielweisshoff.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.danielweisshoff.parser.nodesystem.node.ClassNode;
import com.danielweisshoff.parser.nodesystem.node.EntryNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.RootNode;

public class AST {

	private RootNode root;

	public HashMap<String, ClassNode> classes;
	public ArrayList<EntryNode> entries;

	public AST(RootNode root) {
		this.root = root;

		//Just finding the nodes in the AST
		initClassesDictionary();
		initEntries();
	}

	private void initClassesDictionary() {
		classes = new HashMap<>();

		for (ClassNode cn : root.getClasses())
			classes.put(cn.getName(), cn);
	}

	private void initEntries() {
		entries = new ArrayList<>();

		for (ClassNode cn : root.getClasses()) {
			for (Node n : cn.getFunctions()) {
				if (n instanceof EntryNode)
					entries.add((EntryNode) n);
			}
		}
	}
}
