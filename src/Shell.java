import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.nodesystem.node.EntryNode;
import com.danielweisshoff.parser.Parser;

import java.util.Scanner;

/*TODO
 * - Rechnen mit Klammern soll möglich sein
 */

public class Shell {

    public Shell() {
        System.out.println("Version 0.2");

        Scanner scanner = new Scanner(System.in);
        String input = "";
        do {
            input = scanner.nextLine();
            validate(input);
        }
        while (!input.equals("quit"));
        /* Instant start = Instant.now();
        for (int i = 0; i < 1000; i++) {
        }
        Instant end = Instant.now();
        System.out.println("Done in " + Duration.between(start, end).toMillis() + " ms"); */
    }

    public void validate(String text) {
        Lexer lexer = new Lexer(text);
        Token[] tokens = lexer.tokenizeText();

        for (Token t : tokens)
            t.print();
        Parser parser = new Parser(tokens);
        EntryNode entry = parser.parse();

        Interpreter interpreter = new Interpreter(entry);
        interpreter.run();
    }

    public static void main(String[] args) {
        new Shell();
    }
}
