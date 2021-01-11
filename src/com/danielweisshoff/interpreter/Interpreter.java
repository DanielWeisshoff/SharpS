package com.danielweisshoff.interpreter;

import com.danielweisshoff.interpreter.nodesystem.node.EntryNode;
import com.danielweisshoff.parser.Class;

import java.util.*;


public class Interpreter {

    private final HashMap<String, Class> classes;
    private final List<EntryNode> entries;

    /*TODO
     * - Anstatt Entrienodes sollte der Parser dem Interpreter alle Klassen als Array schicken,
     * denn somit hat der Interpeter volle Kontrolle, denn die Entrypoints sind sowieso in
     * den Klassen gespeichert
     * - Alle Entries aus allen Klassen suchen
     */
    public Interpreter(Class[] classArr) {
        classes = new HashMap<>();
        for (Class c : classArr)
            classes.put(c.getName(), c);
        entries = new ArrayList<>();

        registerEntries(classArr);
    }

    private void registerEntries(Class[] classes) {
        for (Class c : classes) {
            EntryNode[] entries = c.getEntries();
            this.entries.addAll(Arrays.asList(entries));
        }
    }

    public void run() {
        if (entries == null || entries.size() == 0) {
            System.out.println("Keinen Einstiegspunkt gefunden");
            return;
        }
        if (entries.size() == 1)
            entries.get(0).execute();
        else
            entryPointSelection().execute();
    }

    public EntryNode entryPointSelection() {
        int counter = 1;
        System.out.println("Entrypoints:");
        for (EntryNode n : entries) {
            System.out.println("[" + counter + "] " + n.getName());
            counter++;
        }
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        while (input < 0 || input > counter) {
            input = scanner.nextInt();
        }
        return entries.get(input);
    }
}
