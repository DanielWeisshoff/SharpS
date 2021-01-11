import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Class;
import com.danielweisshoff.parser.container.Program;

/*TODO
 * - Rechnen mit Klammern soll möglich sein
 */

public class Shell {

    public Shell() {
        System.out.println("Version 0.2");
        validate(Goethe.readFile());
        /*
        String input = "";
        Scanner scanner = new Scanner(System.in);
        do {
            input = scanner.nextLine();
            validate(input);
        }
        while (!input.equals("quit"));*/
    }

    public void validate(String text) {
        Token[] tokens = new Lexer(text).tokenizeText();

        Program program = new Parser(tokens).parse();
        new Interpreter(program).run();
    }

    public static void main(String[] args) {
        new Shell();
    }
}
