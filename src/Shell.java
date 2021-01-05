
import java.util.ArrayList;
import java.util.Scanner;

import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;

/*TODO
 * - Rechnen mit Klammern soll möglich sein
 * - In Node-System umwandeln
 * - Number-Node
 * - Operation-Node
 */

/**
 * 
 * @author danie
 *
 */
public class Shell {

	public Shell() {
		System.out.println("Version 0.2");
		Scanner scanner = new Scanner(System.in);

		
		while (true) {
			String text = scanner.nextLine();
			validate(text);
		}
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
