package com.danielweisshoff;

import com.danielweisshoff.editor.ColorTheme;
import com.danielweisshoff.editor.Highlighter;
import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Program;

import java.util.Scanner;
import java.time.Duration;
import java.time.Instant;

public class Shell {

    public static void main(String[] args) {
        interpret(Goethe.getProgram());
    }

    public Shell() {
        interpret(Goethe.getProgram());

        String input = "";
        Scanner scanner = new Scanner(System.in);
        do {
            input = scanner.nextLine();
            interpret(input);
        } while (!input.equals("quit"));
        scanner.close();
    }

    public static void interpret(String text) {

        Token[] tokens = new Lexer(text).tokenizeText();
        Goethe.writeTokens(tokens);
        Program program = new Parser(tokens).parse();
        new Interpreter(program).run();
    }

    public static void benchmark(String text) {

        Instant start = Instant.now();
        Token[] tokens = new Lexer(text).tokenizeText();
        Goethe.writeTokens(tokens);

        Instant end = Instant.now();
        Logger.log("Lexer done in " + Duration.between(start, end).toMillis() + "ms");

        start = Instant.now();
        Program program = new Parser(tokens).parse();
        end = Instant.now();
        Logger.log("Parser done in " + Duration.between(start, end).toMillis() + "ms");

        new Interpreter(program).run();
    }

    public void HighLight() {
        String text = Goethe.getProgram();
        Token[] tokens = new Lexer(text).tokenizeText();

        Highlighter.loadTheme(ColorTheme.IntelliJ);
        Highlighter.Highlight(tokens);
    }
}
