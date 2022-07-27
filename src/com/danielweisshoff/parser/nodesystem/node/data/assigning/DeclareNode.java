package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/**
 * Initializes a variable
 */
public class DeclareNode extends Node {
	private final String name;
	public final DataType dataType;

	public DeclareNode(String name, DataType dataType) {
		super(null, null, NodeType.DECLARE_NODE);

		this.name = name;
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	@Override
	public Data run() {

		//generate an id for the variable
		long id = IdRegistry.newID();

		//entry in symboltable
		VariableEntry entry = new VariableEntry(name, id, dataType, new Data());
		Interpreter.symbolTable.addVariable(id, entry);

		return new Data();
	}
}