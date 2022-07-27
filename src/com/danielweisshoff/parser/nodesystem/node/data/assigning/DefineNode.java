package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class DefineNode extends AssignNode {

	public final Node expression;

	public DefineNode(String name, Node expression) {
		super(name, NodeType.DEFINE_NODE);

		this.expression = expression;
	}

	@Override
	public Data run() {

		VariableEntry entry = Interpreter.stm.findVariable(name);

		double val = expression.run().asDouble();
		entry.node.data.setValue(val);

		if (Interpreter.debug) {
			System.out.println(val + ", " + ((VariableNode) entry.node).getDataType());
		}

		return new Data();
	}

}
