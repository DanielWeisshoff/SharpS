package com.danielweisshoff;

import java.time.Duration;
import java.time.Instant;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.parser.Parser;

public class Shell {

    public static boolean benchmark = false;
    public static boolean debug = false;

    public static void main(String[] args) {
        parseArgs(args);

        Logger.enabled = true;
        if (benchmark)
            benchmark();
        else
            run();
        if (benchmark) {
            System.out.println("____________________BENCHMARK____________________\n");
            System.out.println(benchmarks);
            System.out.println("all done in " + benchmarkMS + " ms");
        }
    }

    public static void parseArgs(String[] args) {
        for (String s : args) {
            switch (s) {
            case "-d":
                Shell.debug = true;
                break;
            case "-b":
                Shell.benchmark = true;
                break;
            default:
                System.out.println("Unknown tag " + s);
                System.exit(1);
            }
        }
    }

    public static void run() {

        Goethe.clearLog();

        String text = Goethe.getText();
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser();

        Token[] line;

        while (lexer.hasNextLine()) {
            line = lexer.next();
            if (line.length == 0 || (line.length == 1 && line[0].type() == TokenType.TAB))
                continue;

            parser.parse(line);
        }

        //INTERPRETATION
        Interpreter interpreter = new Interpreter();
        Interpreter.debug = debug;

        Node ast = parser.getAST();
        interpreter.interpret(ast);
    }

    //Modules are running separated and are benchmarked
    public static void benchmark() {
        Goethe.clearLog();

        //LEXING
        Lexer lexer = new Lexer(Goethe.getText());
        start();
        Token[] tokens = lexer.next();

        if (debug)
            for (Token t : tokens)
                System.out.println(t.getDescription());

        stop("LEXER");
        System.out.println(benchmarks);

        //PARSING
        Parser parser = new Parser();
        start();
        parser.parse(tokens);
        stop("PARSER");

        if (debug) {
            parser.printSymbolTable();
            parser.getAST().print(0);
        }

        //INTERPRETATION
        System.out.println("____________________INTERPRETER____________________\n");
        Interpreter interpreter = new Interpreter();
        Interpreter.debug = debug;
        Node ast = parser.getAST();

        //INTERPRETING
        start();
        interpreter.interpret(ast);
        stop("INTERPRETER");
    }

    //
    private static Instant start, end;
    private static String benchmarks = "";
    private static long benchmarkMS;

    public static void start() {
        start = Instant.now();
    }

    public static void stop(String msg) {
        end = Instant.now();
        long time = Duration.between(start, end).toMillis();
        benchmarks += msg + " done in " + time + "ms\n";
        benchmarkMS += time;
    }

}
