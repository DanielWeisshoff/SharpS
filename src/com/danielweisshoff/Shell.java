package com.danielweisshoff;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.logger.Logger.Channel;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.OutNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.VarInitNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PostIncrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.IntegerNode;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.LessNode;
import com.danielweisshoff.parser.nodesystem.node.loops.ForNode;
import com.danielweisshoff.parser.parser.Parser;

public class Shell {

    public static boolean debug = false;

    public static void main(String[] args) {

        //DEBUG testing nested for loops
        // test();
        // System.exit(0);

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
            System.out.println("=====NODE STRUCTURE=====");
            parser.getAST().print(0);
            System.out.println();
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

    public static void test() {

        //FOR 1
        ForNode fn1 = new ForNode();

        VarInitNode vin1 = new VarInitNode("x", DataType.INT, new IntegerNode());
        fn1.init = vin1;

        VariableNode vn1 = new VariableNode("x");
        LessNode ln1 = new LessNode(vn1, new IntegerNode(3));
        fn1.condition = ln1;

        PostIncrementNode pin1 = new PostIncrementNode("x");
        fn1.increment = pin1;

        BlockNode bn1 = new BlockNode("for-body-1");
        fn1.block = bn1;

        //FOR 2
        ForNode fn2 = new ForNode();

        VarInitNode vin2 = new VarInitNode("y", DataType.INT, new IntegerNode());
        fn2.init = vin2;

        VariableNode vn2 = new VariableNode("y");
        LessNode ln2 = new LessNode(vn2, new IntegerNode(3));
        fn2.condition = ln2;

        PostIncrementNode pin2 = new PostIncrementNode("y");
        fn2.increment = pin2;

        ArrayList<Token> t = new ArrayList<>();
        t.add(new Token(TokenType.IDENTIFIER, "x"));
        t.add(new Token(TokenType.COMMA, ""));
        t.add(new Token(TokenType.IDENTIFIER, "y"));
        OutNode on1 = new OutNode(t);

        BlockNode bn2 = new BlockNode("for-body-2");
        bn2.add(on1);

        fn2.block = bn2;

        BlockNode root = new BlockNode("root");
        root.add(fn1);
        fn1.block.add(fn2);
        new Interpreter().interpret(root);
    }
}

/*
* >> x 0
* >> y 0
* >> y 1
* >> y 2
* >> y 3
* >> y 4
* >> y 5
* >> y 6
* >> y 7
* >> y 8
* >> y 9
* >> x 1
        <---- müsste eig y erhöhen
* >> x 2
* >> x 3
* >> x 4
* >> x 5
* >> x 6
* >> x 7
* >> x 8
* >> x 9
 * 
 * 
 */