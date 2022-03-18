package com.danielweisshoff.interpreter;

import com.danielweisshoff.parser.nodesystem.node.Node;

public class Interpreter {

	public void interpret(Node n) {

		n.execute();
	}

}
