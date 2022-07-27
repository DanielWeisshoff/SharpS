package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class DefineNode extends AssignNode {

	public final Node expression;

	public DefineNode(String name, Node expression) {
		super(name, NodeType.DEFINE_NODE);

		this.expression = expression;
	}

	@Override
	public Data run() {

		VariableEntry entry = Interpreter.symbolTable.findVariable(name);

		double val = expression.run().asDouble();
		entry.data.setValue(val);

		if (Interpreter.debug)
			System.out.println(entry.data.data + ", " + entry.dataType);

		return new Data();
	}

}
