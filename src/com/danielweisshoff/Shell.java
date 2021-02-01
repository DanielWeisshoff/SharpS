package com.danielweisshoff;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Program;

import java.time.Duration;
import java.time.Instant;

/*TODO
 * - Rechnen mit Klammern soll möglich sein
 */

public class Shell {

    public Shell() {
        System.out.println("Version 0.2");
        validate(Goethe.getProgram());
        /*
        String input = "";
        Scanner scanner = new Scanner(System.in);
        do {
            input = scanner.nextLine();
            validate(input);
        }
        while (!input.equals("quit"));*/
    }

    public static void main(String[] args) {
        new Shell();
    }

    public void validate(String text) {

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
}
