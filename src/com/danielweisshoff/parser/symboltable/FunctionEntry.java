package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.nodesystem.DataType;

public class FunctionEntry extends Entry {

	private final DataType[] params;
	private final DataType returnType;

	public FunctionEntry(String name, DataType[] params, DataType returnType) {
		super(name, Type.FUNCTION);
		this.params = params;
		this.returnType = returnType;
	}

	public DataType[] getParams() {
		return params;
	}

	public DataType getReturnType() {
		return returnType;
	}
}