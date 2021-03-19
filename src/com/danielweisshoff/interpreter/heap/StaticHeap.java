package com.danielweisshoff.interpreter.heap;

import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.parser.symboltable.SymbolTable;

/**
 * The Space where all functions and all static variables are stored
 */
public class StaticHeap {

    public Node[] heapSpace;

    public StaticHeap(SymbolTable table) {
        /*
         *Alle Einträge des SymbolTables in verweise zu den Variablen/Funktionen umbauen
         */
    }

    public Node get(int id) {
        return heapSpace[id];
    }
}
