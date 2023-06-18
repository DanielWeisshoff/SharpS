
import java.util.Scanner;

import interpreter.Interpreter;
import lexer.Lexer;
import lexer.Token;
import logger.Channel;
import logger.Logger;
import parser.nodesystem.node.diverse.FunctionNode;
import parser.parser.Parser;
import utils.Goethe;
import utils.stopwatch.StopWatch;

public class Shell {

    private static boolean doLogging = false;

    public static void main(String[] args) {
        parseArgs(args);
        FunctionNode AST = compilation();
        interpretation(AST);

        Logger.clearLogFile();
        if (doLogging)
            Logger.writeBufferToFile();
    }

    public static void parseArgs(String[] args) {
        for (String s : args) {
            switch (s) {
            case "-d":
                Lexer.debug = true;
                Parser.debug = true;
                Interpreter.debug = true;
                doLogging = true;
                break;
            case "repl":
                REPL();
                System.exit(0);
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
        Lexer lexer = new Lexer();
        Logger.log("starting lexing process...", Channel.LEXER);

        watch.start("Lexer");
        Token[] tokens = lexer.tokenize(Goethe.getText());
        watch.stop("Lexer");
        Logger.log("done after " + watch.getRound("Lexer").toMillis() + "ms", Channel.LEXER);

        //PARSING
        Parser parser = new Parser();
        Logger.log("starting parsing process...", Channel.PARSER);

        watch.start("Parser");
        parser.parse(tokens);
        watch.stop("Parser");
        Logger.log("done after " + watch.getRound("Parser").toMillis() + "ms", Channel.PARSER);

        //TODO stoopid
        if (Parser.debug) {
            parser.getSymbolTable().print();
            System.out.println("=====NODE STRUCTURE=====");
            parser.getAST().print();
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

    //First steps on a line-by-line cli
    public static void REPL() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        Interpreter interpreter = new Interpreter();
        do {
            StringBuilder sb = new StringBuilder();
            while (true) {
                System.out.print(">");
                input = scanner.nextLine();
                if (input.equals("end"))
                    break;
                sb.append(input + "\n");
            }
            Lexer lexer = new Lexer();
            Token[] tokens = lexer.tokenize(sb.toString());

            Parser parser = new Parser();
            FunctionNode root = parser.parse(tokens);

            interpreter.interpret(root);
        } while (!input.equals("exit"));
        scanner.close();
    }
}