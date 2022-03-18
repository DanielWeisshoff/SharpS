package com.danielweisshoff.parser.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.danielweisshoff.parser.nodesystem.node.EntryNode;

public class Program {

	private final HashMap<String, Class> classes;
	private final List<EntryNode> entries;

	public Program(Class[] classArr) {
		classes = new HashMap<>();
		for (Class c : classArr)
			classes.put(c.getName(), c);
		entries = new ArrayList<>();
		registerEntries(classArr);
	}

	private void registerEntries(Class[] classes) {
		for (Class c : classes) {
			EntryNode[] entries = c.getEntries();
			this.entries.addAll(Arrays.asList(entries));
		}
	}

	public boolean printEntries() {
		if (entries.size() == 1)
			return false;
		int counter = 0;
		System.out.println("Entrypoints:");
		for (EntryNode n : entries) {
			System.out.println("[" + counter + "] " + n.getName());
			counter++;
		}
		return true;
	}

	public EntryNode getEntry(int num) {
		return entries.get(num);
	}
}
