package com.danielweisshoff.parser.symboltable;

public abstract class Entry {

	private final String name;
	private final Type type;

	public Entry(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String getDescription() {
		return name + "\t" + type;
	}
}