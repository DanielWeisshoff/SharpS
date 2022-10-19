package com.danielweisshoff;

import java.time.Duration;
import java.time.Instant;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.logger.Logger.Channel;
import com.danielweisshoff.parser.parser.Parser;

public class Shell {

    public static boolean debug = false;

    public static void main(String[] args) {
        //clearing log file
        Goethe.clearLog();

        parseArgs(args);
        compilation();

        Logger.log("compilation took " + compilationTime + " ms");
        Logger.writeLogs();
    }

    public static void parseArgs(String[] args) {
        for (String s : args) {
            switch (s) {
            case "-d":
                Shell.debug = true;
                Interpreter.debug = true;
                Logger.enabled = true;
                break;
            default:
                System.out.println("Unknown tag " + s);
                System.exit(1);
            }
        }
    }

    private static Instant start, end;
    private static long compilationTime;

    //Modules are running separated and are benchmarked
    public static void compilation() {
        //LEXING
        Logger.log("starting lexing process...", Channel.LEXER);
        Lexer lexer = new Lexer(Goethe.getText());
        start();
        Token[] tokens = lexer.next();
        if (debug)
            for (Token t : tokens)
                Logger.log(t.getDescription(), Channel.LEXER);
        Logger.log("done after " + stop() + "ms", Channel.LEXER);

        //PARSING
        Logger.log("starting parsing process...", Channel.PARSER);
        Parser parser = new Parser();
        start();
        parser.parse(tokens);
        Logger.log("done after " + stop() + "ms", Channel.PARSER);
        if (debug) {
            parser.printSymbolTable();
            parser.getAST().print(0);
        }

        //INTERPRETATION
        Logger.log("starting interpretation...", Channel.INTERPRETER);
        Interpreter interpreter = new Interpreter();
        start();
        interpreter.interpret(parser.getAST());
        Logger.log("done after " + stop() + "ms", Channel.INTERPRETER);
    }

    //timer methods for benchmarks

    public static void start() {
        start = Instant.now();
    }

    public static long stop() {
        end = Instant.now();
        long time = Duration.between(start, end).toMillis();
        compilationTime += time;
        return time;
    }
}
