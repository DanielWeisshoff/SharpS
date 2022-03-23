package com.danielweisshoff.parser;

import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.RootNode;

/*
API for the NodeSystem
?Make Node protected?
*/
public class AST {

	private RootNode root;
	//pointer for Node that is currently visited
	private Node visitNode;

	public AST(RootNode root) {
		this.root = root;
	}
}
