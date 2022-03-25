package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.nodesystem.DataType;

public class VariableEntry extends Entry {

	private final DataType dataType;
	public String value;

	public VariableEntry(String name, DataType dataType, String value) {
		super(name, Type.VARIABLE);
		this.dataType = dataType;
		this.value = value;
	}

	public DataType getDataType() {
		return dataType;
	}

	public String getValue() {
		return value;
	}

}
