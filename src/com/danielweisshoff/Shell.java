package com.danielweisshoff;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.logger.Logger.Channel;
import com.danielweisshoff.parser.nodesystem.node.FunctionNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.utils.Goethe;
import com.danielweisshoff.utils.stopwatch.StopWatch;

public class Shell {

    public static void main(String[] args) {
        parseArgs(args);
        FunctionNode AST = compilation();
        interpretation(AST);

        //Logging
        Logger.clearLogs();
        Logger.writeLogs();
    }

    public static void parseArgs(String[] args) {
        for (String s : args) {
            switch (s) {
            case "-d":
                Lexer.debug = true;
                Parser.debug = true;
                Interpreter.debug = true;
                Logger.enabled = true;
                break;
            default:
                System.out.println("Unknown flag " + s);
                System.exit(1);
            }
        }
    }

    //Compilationsteps are running separately and are benchmarked
    public static FunctionNode compilation() {
        StopWatch watch = new StopWatch();
        watch.start("Compilation");

        //LEXING
        Lexer lexer = new Lexer(Goethe.getText());
        Logger.log("starting lexing process...", Channel.LEXER);

        watch.start("Lexer");
        Token[] tokens = lexer.next();
        watch.stop("Lexer");
        Logger.log("done after " + watch.getRound("Lexer").toMillis() + "ms", Channel.LEXER);

        //PARSING
        Parser parser = new Parser();
        Logger.log("starting parsing process...", Channel.PARSER);

        watch.start("Parser");
        parser.parse(tokens);
        watch.stop("Parser");
        Logger.log("done after " + watch.getRound("Parser").toMillis() + "ms", Channel.PARSER);

        //TODOstoopid
        if (Parser.debug) {
            parser.printSymbolTable();
            System.out.println("=====NODE STRUCTURE=====");
            parser.getAST().print(0);
            System.out.println();
        }

        watch.stop("Compilation");
        Logger.log("compilation took " + watch.getRound("Compilation").toMillis() + " ms");

        return parser.getAST();
    }

    public static void interpretation(FunctionNode AST) {
        StopWatch watch = new StopWatch();

        //INTERPRETATION
        Logger.log("starting interpretation...", Channel.INTERPRETER);
        Interpreter interpreter = new Interpreter();
        watch.start("Interpreter");

        interpreter.interpret(AST);
        watch.stop("Interpreter");
        Logger.log("done after " + watch.getRound("Interpreter").toMillis() + "ms", Channel.INTERPRETER);
    }
}