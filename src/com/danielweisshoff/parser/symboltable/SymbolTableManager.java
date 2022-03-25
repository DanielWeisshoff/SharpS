package com.danielweisshoff.parser.symboltable;

import java.util.ArrayList;

import com.danielweisshoff.logger.Logger;

/**
 * Manages all SymbolTables and builds them in a hierarchical order
 */
public class SymbolTableManager {

	private SymbolTable root;
	private SymbolTable currentTable;
	public boolean deleteTableOnScopeEnd = false;

	//for hashing
	int idCounter = 0;

	//collection of all SymbolTables
	private ArrayList<SymbolTable> lookup = new ArrayList<>();

	private void initRoot() {
		root = new SymbolTable("STATIC_TABLE");
		lookup.add(root);
		currentTable = root;
	}

	public void newScope(String name) {
		if (root == null) {
			initRoot();
			return;
		}

		SymbolTable st = new SymbolTable(name + "_" + (idCounter++));
		st.parent = currentTable;
		currentTable = st;

		Logger.log("[SYMBOLTABLE]: new scope");
		lookup.add(st);
	}

	public void endScope() {
		SymbolTable temp = currentTable;
		currentTable = currentTable.parent;

		if (deleteTableOnScopeEnd) {
			lookup.remove(temp);
		}
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

	public void toRoot() {
		currentTable = root;
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

	public Entry findVariableInScope(String name) {
		return currentTable.findVariable(name);
	}

	public Entry findFunctionInScope(String name) {
		return currentTable.findFunction(name);
	}

	public Entry findStaticVariable(String name) {
		return root.findVariable(name);
	}

	public Entry findStaticFunction(String name) {
		return root.findFunction(name);
	}
}
