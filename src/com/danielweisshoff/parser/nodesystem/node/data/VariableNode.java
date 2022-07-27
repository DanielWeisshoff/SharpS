package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 */

/**
 * Holds the name of a variable and returns the stored data
 */
public class VariableNode extends Node {

	private final String name;

	public VariableNode(String name) {
		super(null, null, NodeType.VARIABLE_NODE);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public DataType getDataType() {
		VariableEntry var = Interpreter.symbolTable.findVariable(name);
		return var.dataType;
	}

	@Override
	public Data run() {

		VariableEntry var = Interpreter.symbolTable.findVariable(name);
		if (var == null)
			new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

		return var.getData();
	}
}
