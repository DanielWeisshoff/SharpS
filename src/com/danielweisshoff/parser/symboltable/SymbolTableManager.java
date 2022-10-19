package com.danielweisshoff.parser.symboltable;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Manages all SymbolTables and builds them in a hierarchical order
 */
public class SymbolTableManager {

    private SymbolTable root;
    private Stack<SymbolTable> tables = new Stack<>();

    //collection of all SymbolTables
    private LinkedList<SymbolTable> pool = new LinkedList<>();

    public boolean deleteTableOnScopeEnd = false;

    public void newScope(String name, int scope) {
        SymbolTable st = new SymbolTable(name, scope);

        if (root == null)
            root = st;
        else {
            st.parent = tables.peek();
            tables.peek().children.add(st);
        }

        tables.add(st);
        pool.add(st);
    }

    public void endScope() {
        if (deleteTableOnScopeEnd)
            pool.remove(tables.peek());
        tables.pop();
    }

    public SymbolTable getCurrentTable() {
        return tables.peek();
    }

    public void clearCurrentTable() {
        tables.peek().clear();
    }

    public void addVariable(long id, VariableEntry entry) {
        tables.peek().addVariable(entry);
    }

    public void addFunction(long id, FunctionEntry entry) {
        tables.peek().addFunction(entry);
    }

    public void addStaticVariable(long id, VariableEntry entry) {
        root.addVariable(entry);
    }

    public void addStaticFunction(long id, FunctionEntry entry) {
        root.addFunction(entry);
    }

    public VariableEntry findVariable(String name) {
        return tables.peek().findVariable(name);
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
        return tables.peek().findVariable(name) != null;
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
