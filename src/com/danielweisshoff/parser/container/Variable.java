package com.danielweisshoff.parser.container;

import com.danielweisshoff.parser.nodesystem.Data;

public class Variable {

    private static int idCounter = 0;

    private final int id;
    private final Data<?> data;
    private final String name;

    public Variable(String name, Data<?> data) {
        this.name = name;
        this.data = data;
        id = idCounter;
        idCounter++;
    }

    public String getName() {
        return name;
    }
}
