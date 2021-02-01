package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 */

/**
 * Holds the name of a variable and returns the stored data
 */
public class VariableNode extends Node {

    private final String name;

    public VariableNode(String name) {
        super(null, null);
        this.name = name;
    }

    @Override
    public Data<?> execute() {
        Data<?> data = new Data<>(null, DataType.NULL);
        if (Parser.variables.containsKey(name)) {
            data = Parser.variables.get(name);
        }
        return data;
    }
}
