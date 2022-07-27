package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class PtrInitNode extends AssignNode {

	private final String name;
	public final DataType dataType;
	public final String adress;
	public boolean isPointer = false;

	public PtrInitNode(String name, DataType dataType, String adress) {
		super(name, NodeType.PTR_INIT_NODE);

		this.name = name;
		this.dataType = dataType;
		this.adress = adress;
	}

	@Override
	public Data run() {

		//generate an id for the variable
		long id = IdRegistry.newID();
		VariableEntry ve = Interpreter.symbolTable.findVariable(adress);
		Data data = ve.getData();

		VariableEntry entry = new VariableEntry(name, id, dataType, data);
		Interpreter.symbolTable.addVariable(id, entry);

		if (Interpreter.debug)
			System.out.println(data.data + ", " + dataType);

		return new Data();
	}

}
