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

	public static void main(String[] args) {
		interpret();
		//benchmark();
	}

	public static void interpret() {

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

		Interpreter interpreter = new Interpreter();
		Node ast = parser.getAST();
		interpreter.interpret(ast);
	}

	public static void benchmark() {

		Goethe.clearLog();

		String text = Goethe.getText();
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser();

		Token[] line;
		//int counter = 1;

		Instant start = Instant.now();
		while (lexer.hasNextLine()) {
			line = lexer.nextLine();
			if (line.length == 0 || (line.length == 1 && line[0].type() == TokenType.TAB))
				continue;

			parser.parseLine(line);
			// //DEBUGGING
			// String msg = "\n[" + counter++ + "] ";
			// for (Token t : line)
			// 	msg += t.type() + " ";
			// Logger.log(msg);

		}
		Instant end = Instant.now();
		System.out.println("Lexer + Parser done in " + Duration.between(start, end).toMillis() + "ms");

		Interpreter interpreter = new Interpreter();
		Node ast = parser.getAST();
		interpreter.interpret(ast);

		System.exit(0);
	}
}
