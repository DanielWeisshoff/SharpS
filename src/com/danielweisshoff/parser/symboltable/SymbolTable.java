package com.danielweisshoff.parser.symboltable;

import java.util.HashMap;

public class SymbolTable {

	public SymbolTable parent;
	private String name;

	private final HashMap<String, FunctionEntry> functionTable = new HashMap<>();
	private final HashMap<String, VariableEntry> variableTable = new HashMap<>();

	public SymbolTable(String name) {
		this.name = name;
	}

	public void clear() {
		functionTable.clear();
		variableTable.clear();
	}

	public void addFunction(String name, FunctionEntry entry) {
		functionTable.put(name, entry);
	}

	public void addVariable(String name, VariableEntry entry) {
		variableTable.put(name, entry);
	}

	/*
	 *Sucht in sich und allen seinen �bergeordneten SymbolTables nach dem Eintrag und gibt diesen zur�ck
	 */
	public FunctionEntry findFunction(String name) {
		FunctionEntry fe = functionTable.get(name);
		if (fe != null)
			return fe;

		if (parent != null)
			return parent.findFunction(name);

		return null;
	}

	public VariableEntry findVariable(String name) {
		VariableEntry fe = variableTable.get(name);
		if (fe != null)
			return fe;

		else if (parent != null)
			return parent.findVariable(name);

		return null;
	}

	// public Entry[] getEntries() {
	// 	Entry[] arr;
	// 	arr = new Entry[entries.size()];
	// 	entries.toArray(arr);
	// 	return arr;
	// }

	public String getName() {
		String parentName = "";
		if (parent != null)
			parentName = parent.getName();
		return parentName + "/" + name;
	}
}