package com.danielweisshoff.interpreter;

import com.danielweisshoff.parser.container.Program;

import java.util.*;


public class Interpreter {

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

