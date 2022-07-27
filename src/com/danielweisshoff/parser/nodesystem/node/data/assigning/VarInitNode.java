package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class VarInitNode extends AssignNode {

	private final String name;
	public final DataType dataType;
	public final NumberNode expression;
	public boolean isPointer = false;

	public VarInitNode(String name, DataType dataType, NumberNode expression) {
		super(name, NodeType.INIT_NODE);

		this.name = name;
		this.dataType = dataType;
		this.expression = expression;
	}

	@Override
	public Data run() {
		//checking semantics
		Interpreter.conversionChecker.convert(dataType, expression);

		//generate an id for the variable
		long id = IdRegistry.newID();

		VariableNode vn = new VariableNode(name, dataType);
		vn.data = expression.run();

		VariableEntry ve = new VariableEntry(name, id, vn);
		Interpreter.stm.addVariable(id, ve);

		if (Interpreter.debug) {
			Data data = expression.run();
			System.out.println(data.data + ", " + dataType);
		}

		return new Data();
	}

}
