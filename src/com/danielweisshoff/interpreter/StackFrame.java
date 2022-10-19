package com.danielweisshoff.interpreter;

import java.util.ArrayList;
import java.util.HashMap;

import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class StackFrame {

    public StackFrame parent;

    private final HashMap<String, VariableEntry> variableStrTable = new HashMap<>();
    //TODO? gebraucht?
    //for debugging
    public ArrayList<VariableEntry> variables = new ArrayList<>();

    public void addVariable(String name, NumberNode node) {
        //generate an id for the variable
        long id = IdRegistry.newID();
        VariableEntry ve = new VariableEntry(name, id, node);

        variableStrTable.put(name, ve);
        variables.add(ve);
    }

    public VariableEntry findVariable(String name) {
        VariableEntry fe = variableStrTable.get(name);
        if (fe != null)
            return fe;
        else if (parent != null)
            return parent.findVariable(name);
        else
            return null;
    }
}
