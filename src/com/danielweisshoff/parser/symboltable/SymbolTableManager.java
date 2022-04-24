package com.danielweisshoff.parser.symboltable;

import java.util.ArrayList;

import com.danielweisshoff.logger.Logger;

/**
 * Manages all SymbolTables and builds them in a hierarchical order
 */
public class SymbolTableManager {

	public boolean deleteTableOnScopeEnd = false;

	//for hashing
	private int idCounter = 0;

	private SymbolTable root;
	private SymbolTable currentTable;
	//collection of all SymbolTables
	private ArrayList<SymbolTable> lookup = new ArrayList<>();

	private void initRoot() {
		root = new SymbolTable("ROOT");
		lookup.add(root);
		currentTable = root;
	}

	public void newScope(String name) {
		if (root == null) {
			initRoot();
			return;
		}

		String tableName = name + "_" + (idCounter++);
		SymbolTable st = new SymbolTable(tableName);
		st.parent = currentTable;
		currentTable = st;

		Logger.log("[SYMBOLTABLE]: new scope '" + tableName + "'");
		lookup.add(st);
	}

	public void endScope() {
		SymbolTable temp = currentTable;
		currentTable = currentTable.parent;

		if (deleteTableOnScopeEnd)
			lookup.remove(temp);
	}

	public void clearCurrentTable() {
		currentTable.clear();
	}

	public void addVariableToScope(String name, VariableEntry entry) {
		currentTable.addVariable(name, entry);
	}

	public void addFunctionToScope(String name, FunctionEntry entry) {
		currentTable.addFunction(name, entry);
	}

	public void addVariableToStatic(String name, VariableEntry entry) {
		root.addVariable(name, entry);
	}

	public void addFunctionToStatic(String name, FunctionEntry entry) {
		root.addFunction(name, entry);
	}

	public void gotoRoot() {
		currentTable = root;
	}

	public VariableEntry findVariableInScope(String name) {
		return currentTable.findVariable(name);
	}

	public FunctionEntry findFunctionInScope(String name) {
		return currentTable.findFunction(name);
	}

	public VariableEntry findStaticVariable(String name) {
		return root.findVariable(name);
	}

	public FunctionEntry findStaticFunction(String name) {
		return root.findFunction(name);
	}

	public boolean variableExists(String name) {
		return currentTable.findVariable(name) != null;
	}

	public boolean functionExists(String name) {
		return currentTable.findFunction(name) != null;
	}
	// public void print() {
	// 	System.out.println("=====SYMBOLTABLE=====");
	// 	for (SymbolTable st : lookup) {
	// 		Entry[] arr = st.getEntries();
	// 		System.out.println(st.getName());
	// 		for (Entry e : arr)
	// 			System.out.println(e.getDescription());
	// 		System.out.println();
	// 	}
	// }
}
