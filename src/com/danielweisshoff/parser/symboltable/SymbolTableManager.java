package com.danielweisshoff.parser.symboltable;

import java.util.ArrayList;

import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;

/**
 * Manages all SymbolTables and builds them in a hierarchical order
 */
public class SymbolTableManager {

	private SymbolTable root;
	private SymbolTable currentTable;

	//collection of all SymbolTables
	private ArrayList<SymbolTable> lookup = new ArrayList<>();

	public void newScope(String name) {
		if (root == null) {
			root = new SymbolTable("Static Table");
			lookup.add(root);
			currentTable = root;
			return;
		}

		SymbolTable st = new SymbolTable(name);
		st.parent = currentTable;
		currentTable = st;

		Logger.log("[SYMBOLTABLE]: new scope");
		lookup.add(st);
	}

	public void endScope() {
		currentTable = currentTable.parent;

		if (currentTable == null)
			new PError("SymboltableManager out of bounds");
	}

	public void addToScope(Entry entry) {
		currentTable.add(entry);
	}

	public void addToStatic(Entry entry) {
		root.add(entry);
	}

	public void toRoot() {
		currentTable = root;
	}

	public void print() {
		System.out.println("=====SYMBOLTABLE=====");
		for (SymbolTable st : lookup) {
			Entry[] arr = st.getEntries();
			System.out.println(st.getName());
			for (Entry e : arr)
				System.out.println(e.getDescription());
			System.out.println();
		}
	}

	public Entry findInCurrentScope(String name, Type type) {
		return currentTable.find(name, type);
	}

	public Entry findStatic(String name, Type type) {
		return root.find(name, type);
	}
}
