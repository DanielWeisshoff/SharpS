package com.danielweisshoff;

import com.danielweisshoff.lexer.Lexer;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;

public class Shell {

	public static void main(String[] args) {

		Goethe.clearLog();

		String text = Goethe.getText();
		Lexer lexer = new Lexer(text);
		Parser parser = new Parser();

		Token[] line;
		int counter = 1;
		while (lexer.hasNextLine()) {
			line = lexer.nextLine();
			if (line.length == 0 || (line.length == 1 && line[0].type() == TokenType.TAB))
				continue;

			parser.parseLine(line);

			//DEBUGGING
			String msg = "\n[" + counter++ + "] ";
			for (Token t : line)
				msg += t.type() + " ";
			Logger.log(msg);

		}

		parser.getAST().execute();
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
