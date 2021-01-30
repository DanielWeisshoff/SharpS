package com.danielweisshoff.parser.container;

import com.danielweisshoff.parser.nodesystem.node.EntryNode;
import com.danielweisshoff.parser.nodesystem.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Class {

    private final String name;
    //private final HashMap<String, Node> functions;
    private final List<EntryNode> entries;
    //private final HashMap<String, Variable> attributes;

    public Class(String name) {
        this.name = name;
       // functions = new HashMap<>();
       // attributes = new HashMap<>();
        entries = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

   /* public void addAttribute(Variable v) {
        attributes.put(v.getName(), v);
    }*/

    public void addEntry(EntryNode entryNode) {
        entries.add(entryNode);
    }

    public EntryNode[] getEntries() {
        EntryNode[] arr = new EntryNode[entries.size()];
        return entries.toArray(arr);
    }

 /*   public void addFunction(String functionName, Node functionRoot) {
        functions.put(functionName, functionRoot);
    }*/
}
