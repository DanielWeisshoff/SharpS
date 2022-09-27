package com.danielweisshoff.parser.symboltable;

import java.util.LinkedList;

/**
 * Manages all SymbolTables and builds them in a hierarchical order
 */
public class SymbolTableManager {

    public boolean deleteTableOnScopeEnd = false;

    private SymbolTable root;
    private SymbolTable currentTable;
    //collection of all SymbolTables
    private LinkedList<SymbolTable> lookup = new LinkedList<>();

    public SymbolTableManager() {
        root = new SymbolTable("ROOT");
        lookup.add(root);
        currentTable = root;
    }

    public void newScope(String name) {
        SymbolTable st = new SymbolTable(name);
        currentTable.children.add(st);
        st.parent = currentTable;
        currentTable = st;

        lookup.add(st);
    }

    public void endScope() {
        SymbolTable temp = currentTable;
        currentTable = currentTable.parent;

        if (deleteTableOnScopeEnd)
            lookup.remove(temp);
    }

    public SymbolTable getCurrentTable() {
        return currentTable;
    }

    public void clearCurrentTable() {
        currentTable.clear();
    }

    public void addVariable(long id, VariableEntry entry) {
        currentTable.addVariable(entry);
    }

    public void addFunction(long id, FunctionEntry entry) {
        currentTable.addFunction(entry);
    }

    public void addStaticVariable(long id, VariableEntry entry) {
        root.addVariable(entry);
    }

    public void addStaticFunction(long id, FunctionEntry entry) {
        root.addFunction(entry);
    }

    public VariableEntry findVariable(String name) {
        return currentTable.findVariable(name);
    }

    public VariableEntry findStaticVariable(String name) {
        return root.findVariable(name);
    }
    // public VariableEntry findVariable(long id) {
    // 	return currentTable.findVariable(id);
    // }

    // public FunctionEntry findFunction(String name) {
    // 	return currentTable.findFunction(name);
    // }

    // public FunctionEntry findStaticFunction(String name) {
    // 	return root.findFunction(name);
    // }

    public boolean lookupVariable(String name) {
        return currentTable.findVariable(name) != null;
    }

    // public boolean lookupFunction(String name) {
    // 	return currentTable.findFunction(name) != null;
    // }

    public void print() {
        System.out.println("=====SYMBOLTABLE=====");
        root.print(0);
        System.out.println();
    }
}
