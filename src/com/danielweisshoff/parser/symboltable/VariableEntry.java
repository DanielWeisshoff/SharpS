package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

public class VariableEntry extends Entry {

	public final DataType dataType;
	public Data data;

	public VariableEntry(String name, long id, DataType dataType, Data data) {
		super(name, id, Type.VARIABLE);
		this.dataType = dataType;
		this.data = data;
	}

	public DataType getDataType() {
		return dataType;
	}

	public Data getData() {
		return data;
	}
}
