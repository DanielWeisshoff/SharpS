package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class ArrSetFieldNode extends Node {

	private final String name;
	private final NumberNode index;
	private final NumberNode value;

	public ArrSetFieldNode(String name, NumberNode index, NumberNode value) {
		super(null, null, NodeType.ARRAY_SET_FIELD_NODE);

		this.name = name;
		this.index = index;
		this.value = value;
	}

	@Override
	public Data run() {
		VariableEntry ve = Interpreter.symbolTable.findVariable(name);
		ve.data.setValue(value.run().asDouble());

		return new Data();
	}
}
