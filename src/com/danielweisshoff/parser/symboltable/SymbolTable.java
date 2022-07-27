package com.danielweisshoff.parser.symboltable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

	public SymbolTable parent;
	private String name;
	private int depth;

	private final HashMap<String, FunctionEntry> functionStrTable = new HashMap<>();
	private final HashMap<String, VariableEntry> variableStrTable = new HashMap<>();

	//for debugging
	private ArrayList<VariableEntry> variables = new ArrayList<>();

	public SymbolTable(String name, int depth) {
		this.name = name;
		this.depth = depth;
	}

	public void clear() {
		functionStrTable.clear();
		variableStrTable.clear();

		variables.clear();
	}

	public void addFunction(FunctionEntry entry) {
		functionStrTable.put(entry.getName(), entry);
	}

	public void addVariable(VariableEntry entry) {
		variableStrTable.put(entry.getName(), entry);

		variables.add(entry);
	}

	public FunctionEntry findFunction(String name) {
		FunctionEntry fe = functionStrTable.get(name);
		if (fe != null)
			return fe;
		else if (parent != null)
			return parent.findFunction(name);
		else
			return null;
	}

	public VariableEntry findVariable(String name) {
		VariableEntry fe = variableStrTable.get(name);
		if (fe != null)
			return fe;
		else if (parent != null)
			return parent.findVariable(name);
		else
			return null;
	}

	public void print() {
		String offset = "";
		String nameOffset = "";

		offset += "\t";
		for (int i = 0; i < depth; i++) {
			offset += "\t";
			nameOffset += "\t";
		}

		System.out.println(nameOffset + "" + name + ":");

		for (VariableEntry ve : variables)
			System.out.print(offset + "" + ve.getName() + ", " + ve.getType() + "\n");
	}

	public String getName() {
		String parentName = "";
		if (parent != null)
			parentName = parent.getName();
		return parentName + "/" + name;
	}
}