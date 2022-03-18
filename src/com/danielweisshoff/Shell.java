package com.danielweisshoff;

import java.util.Scanner;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.EntryNode;

public class Shell {

	public static void main(String[] args) {

		String text = Goethe.getText();
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser();

		Token[] line;
		int counter = 1;
		while (lexer.hasNextLine()) {
			line = lexer.nextLine();
			parser.parseLine(line);

			//DEBUGGING
			System.out.print("\n[" + counter++ + "] ");
			for (Token t : line)
				System.out.print(t.type() + " ");
		}
		System.exit(0);
	}

	//
	// ERSTMAL UNWICHTIG
	//

	// public static void benchmark(String text) {

	// 	Instant start = Instant.now();
	//// 	Token[] tokens = new Lexer(text).tokenizeText();
	// 	Goethe.writeTokens(tokens);

	// 	Instant end = Instant.now();
	// 	Logger.log("Lexer done in " + Duration.between(start, end).toMillis() + "ms");

	// 	start = Instant.now();
	// 	Program program = new Parser(tokens).parse();
	// 	end = Instant.now();
	// 	Logger.log("Parser done in " + Duration.between(start, end).toMillis() + "ms");

	// 	new Interpreter(program).run();
	// }

	// //Gives out the code in colorized format
	// public void HighLight() {
	// 	String text = Goethe.getNextLine();
	// 	Token[] tokens = new Lexer(text).tokenizeText();

	// 	Highlighter.loadTheme(ColorTheme.IntelliJ);
	// 	Highlighter.Highlight(tokens);
	// }
}
