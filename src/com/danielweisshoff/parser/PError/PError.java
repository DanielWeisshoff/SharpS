package com.danielweisshoff.parser.PError;

import com.danielweisshoff.Goethe;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;

/**
 * Logs the error message and exits the program
 */
public abstract class PError {

	private String msg;

	private Token[] tokens;

	// public PError(String msg, int line, int pos) {
	// }

	public PError(String msg, Token... tokens) {
		this.msg = msg;
		this.tokens = tokens;

		printInfo();

		//TODO Loggen!!
		//Logger.log(errorMessage);
		System.exit(0);
	}

	private void printInfo() {
		String colorRed = "\u001b[31m";
		String colorReset = "\u001B[0m";

		//Head message
		System.out.println(colorRed + msg + ":\n" + colorReset);

		//printing the error line
		if (tokens.length >= 1) {
			int lineNumber = tokens[0].line;

			//print previous 2 lines, if existing
			if (lineNumber >= 3) {
				System.out.println((lineNumber - 2) + "|" + Goethe.getLine(lineNumber - 2));
				System.out.println((lineNumber - 1) + "|" + Goethe.getLine(lineNumber - 1));
			}
			String line = Goethe.getLine(lineNumber);

			System.out.println(lineNumber + "|" + line);
		}

		//offset
		System.out.print("  ");

		//printing the markers
		int pos = 0;
		for (Token t : tokens) {
			//TODO generelle funktion fuer whitespace nutzen
			if (t.type() == TokenType.TAB)
				continue;

			//offset till error token
			for (int i = pos; i <= t.end; i++) {
				if (pos < t.start)
					System.out.print(" ");
				else
					System.out.print(colorRed + "+");
				pos++;
			}
			System.out.print(colorReset);
		}
	}
}
