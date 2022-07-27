package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 */

/**
 * Holds the name of a variable and returns the stored data
 */
public class VariableNode extends NumberNode {

	private final String name;
	private final DataType dataType;

	public VariableNode(String name, DataType dataType) {
		super(null, null, NodeType.VARIABLE_NODE);
		this.name = name;
		this.dataType = dataType;
	}

	public String getName() {
		return name;
	}

	public DataType getDataType() {
		return dataType;
	}

	@Override
	public Data run() {

		VariableEntry var = Interpreter.stm.findVariable(name);
		if (var == null)
			new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

		return data;
	}
}
