package com.danielweisshoff.parser.symboltable;

import com.danielweisshoff.parser.PError;

import java.util.ArrayList;

public class SymbolTable {

	public SymbolTable parent;
	private final ArrayList<Entry> entries = new ArrayList<>();
	private String name;

	public SymbolTable(String name) {
		this.name = name;
	}

	public void add(Entry entry) {
		entries.add(entry);
	}

	/*
	 *Sucht in sich und allen seinen �bergeordneten SymbolTables nach dem Eintrag und gibt diesen zur�ck
	 */
	public Entry find(String name, Type type) {
		for (Entry e : entries) {
			if (e.getName().equals(name) && e.getType() == type)
				return e;
		}
		if (parent != null)
			return parent.find(name, type);

		return null;
	}

	public Entry[] getEntries() {
		Entry[] arr;
		arr = new Entry[entries.size()];
		entries.toArray(arr);
		return arr;
	}

	public String getName() {
		String parentName = "";
		if (parent != null)
			parentName = parent.getName();
		return parentName + "/" + name;
	}
}