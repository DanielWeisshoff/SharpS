package com.danielweisshoff.parser.symboltable;

public abstract class Entry {

	private final String name;
	private final Type type;
	private final long id;

	public Entry(String name, long id, Type type) {
		this.name = name;
		this.id = id;
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

	public long getID() {
		return id;
	}
}