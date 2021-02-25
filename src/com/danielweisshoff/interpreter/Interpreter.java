package com.danielweisshoff.interpreter;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.container.Program;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;


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

        Instant start = Instant.now();
        Logger.log("--->Starting program on entry " + input + "<---");
        Data<?> errorCode = program.getEntry(input).execute();
        Instant end = Instant.now();

        Logger.log("Program finished in " + Duration.between(start, end).toMillis() + "ms");
        Logger.log("Exited program with code " + errorCode.getData());
    }
}

