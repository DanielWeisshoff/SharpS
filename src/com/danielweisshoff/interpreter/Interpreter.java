package com.danielweisshoff.interpreter;

import com.danielweisshoff.parser.container.Program;

import java.util.*;


public class Interpreter {

    /*TODO
     * - Anstatt Entrienodes sollte der Parser dem Interpreter alle Klassen als Array schicken,
     * denn somit hat der Interpeter volle Kontrolle, denn die Entrypoints sind sowieso in
     * den Klassen gespeichert
     * - Alle Entries aus allen Klassen suchen
     */
    private final Program program;

    public Interpreter(Program program) {
        this.program = program;
    }


    public void run() {
        program.printEntries();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nselect: ");
        int input = scanner.nextInt();
        program.getEntry(input).execute();
    }
}

