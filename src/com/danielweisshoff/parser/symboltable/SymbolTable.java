package com.danielweisshoff.parser.symboltable;


import com.danielweisshoff.parser.PError;

import java.util.ArrayList;

public class SymbolTable {

    public SymbolTable parent;
    private final ArrayList<Entry> entries = new ArrayList<>();
    private long idCounter; // Bei 32-Bit Systemen sollte es ein int sein
    private String name;

    public SymbolTable(String name) {
        this.name = name;
    }


    public void add(String name, DataType type, ReturnType dataType) {
        entries.add(new Entry(name, type, dataType, idCounter));
        idCounter++;
    }

    /*
     *Sucht in sich und allen seinen übergeordneten SymbolTables nach dem Eintrag und gibt diesen zurück
     */
    public Entry get(String name) {
        for (Entry e : entries) {
            if (e.getName().equals(name))
                return e;
        }
        if (parent != null)
            return parent.get(name);

        new PError("Parser Error: Die Variable/Funktion '" + name + "' existiert nicht");
        return null;
    }

    public Entry[] getEntries() {
        Entry[] arr;
        arr = new Entry[entries.size()];
        entries.toArray(arr);
        return arr;
    }

    public String getName() {
        StringBuilder sb = new StringBuilder();
        String parentName = "";
        if (parent != null)
            parentName = parent.getName();
        return parentName + "/" + name;
    }

    public void setParent(SymbolTable t) {
        parent = t;
    }
}