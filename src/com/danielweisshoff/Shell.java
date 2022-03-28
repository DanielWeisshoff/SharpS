package com.danielweisshoff;

import java.time.Duration;
import java.time.Instant;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class Shell {

	public static boolean benchmark = true;
	private static Instant start, end;

	public static void main(String[] args) {

		Goethe.clearLog();

		String text = Goethe.getText();
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser();

		Token[] line;

		while (lexer.hasNextLine()) {
			line = lexer.nextLine();
			if (line.length == 0 || (line.length == 1 && line[0].type() == TokenType.TAB))
				continue;

			parser.parseLine(line);
		}

		//INTERPRETATION
		Interpreter interpreter = new Interpreter();
		interpreter.stepper = false;

		if (benchmark)
			start = Instant.now();

		Node ast = parser.getAST();
		interpreter.interpret(ast);

		if (benchmark) {
			end = Instant.now();
			System.out.println("Interpreter done in " + Duration.between(start, end).toMillis() + "ms");
		}
	}
}
