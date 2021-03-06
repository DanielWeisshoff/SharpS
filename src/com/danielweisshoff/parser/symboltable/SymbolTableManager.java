package com.danielweisshoff.parser.symboltable;

import java.util.ArrayList;

/**
 * Manages all SymbolTables and builds them in a hierarchical order
 */
public class SymbolTableManager {

    private final SymbolTable root;
    private SymbolTable currentTable;

    private ArrayList<SymbolTable> lookup = new ArrayList<>();


    public SymbolTableManager() {
        root = new SymbolTable("Static Table");
        lookup.add(root);
        currentTable = root;
    }

    public void newScope(String name) {
        SymbolTable st = new SymbolTable(name);
        st.parent = currentTable;
        currentTable = st;

        lookup.add(st);
    }

    public void endScope() {
        currentTable = currentTable.parent;
    }

    public void addToScope(String name, DataType type, ReturnType dataType) {
        currentTable.add(name, type, dataType);
    }

    public void addToStatic(String name, DataType type, ReturnType dataType) {
        root.add(name, type, dataType);
    }

    public void toRoot() {
        currentTable = root;
    }

    public void print() {
        for (SymbolTable st : lookup) {
            Entry[] arr = st.getEntries();
            System.out.println(st.getName());
            for (Entry e : arr)
                System.out.println(e.getDescription());
            System.out.println();
        }
    }
}

