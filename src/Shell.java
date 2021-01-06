import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;

/*TODO
 * - Rechnen mit Klammern soll möglich sein
 * - Equation funktioniert nicht
 */

/**
 * input bis jetzt nur zeilenweise
 *
 * @author danie
 */
public class Shell {

    public Shell() {
        System.out.println("Version 0.2");
        Scanner scanner = new Scanner(System.in);

        /* PERFORMANCE TESTING
        *
        Instant start = Instant.now();
        for (int i = 0; i < 20000000; i++) {

        }
        Instant end = Instant.now();
        System.out.println("Done in " + Duration.between(start, end).toMillis() + " ms");
        */

        String input;
        do {
            input = scanner.nextLine();
            validate(input);
        } while (!input.equals("shut"));
        System.out.println("Process Terminated");
    }

    public void validate(String text) {
        Lexer lexer = new Lexer(text);
        Token t = null;
        ArrayList<Token> tokens = new ArrayList<Token>();
        do {
            t = lexer.nextToken();
            tokens.add(t);
            t.print();
        } while (t.type() != TokenType.EOF);

        Parser parser = new Parser();
        parser.parse(tokens);
    }

    public static void main(String[] args) {
        new Shell();
    }
}
