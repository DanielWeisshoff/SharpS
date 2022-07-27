package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class VarInitNode extends AssignNode {

	private final String name;
	public final DataType dataType;
	public final Node expression;
	public boolean isPointer = false;

	public VarInitNode(String name, DataType dataType, Node expression) {
		super(name, NodeType.INIT_NODE);

		this.name = name;
		this.dataType = dataType;
		this.expression = expression;
	}

	@Override
	public Data run() {
		//checking semantics
		Interpreter.conversionChecker.convert(dataType, expression);

		Data data = expression.run();

		//generate an id for the variable
		long id = IdRegistry.newID();

		VariableEntry entry = new VariableEntry(name, id, dataType, data);
		Interpreter.symbolTable.addVariable(id, entry);

		if (Interpreter.debug)
			System.out.println(data.data + ", " + dataType);

		return new Data();
	}

}
