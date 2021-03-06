package com.danielweisshoff.parser.symboltable;

class Entry {

    private final String name;
    private final DataType type;
    private final ReturnType returnType;
    private final long id;

    public Entry(String name, DataType type, ReturnType dataType, long id) {
        this.name = name;
        this.type = type;
        this.returnType = dataType;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return name + "\t" + type + "\t" + type + "\t" + id;
    }
}