package com.danielweisshoff.parser.PError;

import com.danielweisshoff.Goethe;
import com.danielweisshoff.lexer.Token;

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

		//printing the markers
		int pos = 0;
		for (Token t : tokens) {

			//System.out.println("pos: " + tokens[0].start + " " + tokens[0].end);
			for (int i = pos; i < t.start; i++)
				System.out.print(" ");

			//offset
			System.out.print("  ");

			for (int i = t.start; i <= t.end; i++)
				System.out.print(colorRed + "+");
			System.out.print(colorReset);
		}
	}
}
