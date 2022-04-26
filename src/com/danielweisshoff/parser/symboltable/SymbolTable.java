package com.danielweisshoff.parser.symboltable;

import java.util.HashMap;

public class SymbolTable {

	public SymbolTable parent;
	private String name;

	private final HashMap<Long, FunctionEntry> functionTable = new HashMap<>();
	private final HashMap<Long, VariableEntry> variableTable = new HashMap<>();
	//TODO idk man
	private final HashMap<String, FunctionEntry> functionStrTable = new HashMap<>();
	private final HashMap<String, VariableEntry> variableStrTable = new HashMap<>();

	public SymbolTable(String name) {
		this.name = name;
	}

	public void clear() {
		functionTable.clear();
		variableTable.clear();

		functionStrTable.clear();
		variableStrTable.clear();
	}

	public void addFunction(FunctionEntry entry) {
		functionTable.put(entry.getID(), entry);
		functionStrTable.put(entry.getName(), entry);
	}

	public void addVariable(VariableEntry entry) {
		variableTable.put(entry.getID(), entry);
		variableStrTable.put(entry.getName(), entry);
	}

	/*
	 *Sucht in sich und allen seinen �bergeordneten SymbolTables nach dem Eintrag und gibt diesen zur�ck
	 */
	public FunctionEntry findFunction(String name) {
		FunctionEntry fe = functionStrTable.get(name);
		if (fe != null)
			return fe;

		if (parent != null)
			return parent.findFunction(name);

		return null;
	}

	public VariableEntry findVariable(String name) {
		VariableEntry fe = variableStrTable.get(name);
		if (fe != null)
			return fe;

		else if (parent != null)
			return parent.findVariable(name);

		return null;
	}

	public VariableEntry findVariable(long id) {
		VariableEntry fe = variableTable.get(id);
		if (fe != null)
			return fe;

		else if (parent != null)
			return parent.findVariable(id);

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