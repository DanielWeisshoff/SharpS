package com.danielweisshoff.interpreter;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.node.EntryNode;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.container.Program;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Scanner;


public class Interpreter {

    private final Program program;
    private final HashMap<Integer, Node> staticHeap = new HashMap<>();

    public Interpreter(Program program) {
        this.program = program;
    }


    public void run() {
        Data<?> errorCode;

        int input = 0;
        if (program.printEntries()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nselect: ");
            input = scanner.nextInt();
        }
        EntryNode selection = program.getEntry(input);
        String entry = selection.getName();
        Instant start = Instant.now();
        Logger.log("--->Starting program on entry " + entry + "<---");
        Instant end = Instant.now();

        /*
         * PROGRAM EXECUTION
         */
        errorCode = selection.execute();

        Logger.log("Program finished in " + Duration.between(start, end).toMillis() + "ms");
        Logger.log("Exited program with code " + errorCode.getData());
    }
}

