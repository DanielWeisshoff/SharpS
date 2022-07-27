package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/*
Saves the name of a vatiable and searches for its id at runtime
*/
public class PointerNode extends DeclareNode {

	public String adress;

	public PointerNode(String name) {
		super(name, DataType.POINTER);
	}

	@Override
	public Data run() {
		System.out.println(adress + " name");
		return new Data();
	}
}
