package com.danielweisshoff.parser;

import java.util.ArrayList;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;

public class Parser {

	public void parse(ArrayList<Token> tokens) {

		if (tokens.get(0).isNumeric())
			doCalculatingStuff(tokens);
		if (tokens.size() >= 2) {
			if (tokens.get(0).isOP()
					&& tokens.get(1).isNumeric())
				doCalculatingStuff(tokens);
		}
	}

	private void doCalculatingStuff(
			ArrayList<Token> tokens) {

		// EOF entfernen
		tokens.remove(tokens.size() - 1);

		// Gültige Operatoren als Vorzeichen in Nummertoken
		// einfügen
		Token[] tokenArray = signNumbers(tokens);

		// falls == enthalten ist, gleichung checken
		for (int i = 0; i < tokens.size() - 1; i++) {
			if (tokenArray[i].type() == TokenType.EQUALS
					&& tokenArray[i + 1]
							.type() == TokenType.EQUALS) {
				createEquation(tokens, i);
				return;
			}
		}
		// Ansonsten normale Rechnung ausführen
		Calculation calculation = new Calculation(
				tokenArray);
		System.out.println(calculation.getResult());
	}

	// Wandelt Zahlen je nach vorzeichen um
	private Token[] signNumbers(ArrayList<Token> tokens) {
		// Erste Zahl ganz links validieren
		if (tokens.size() >= 2) {

			if (tokens.get(1).isNumeric()
					&& tokens.get(0)
							.type() == TokenType.SUB) {
				tokens.get(1)
						.setValue(
								"-" + tokens
										.get(1)
										.getValue());
				tokens.remove(0);
			}

			for (int i = 1; i < tokens.size(); i++) {
				if (tokens.get(i).isNumeric()
						&&
						tokens.get(i - 1)
								.type() == TokenType.SUB
						&&
						tokens.get(i - 2).isOP()) {
					tokens.get(i)
							.setValue(
									"-" + tokens
											.get(i)
											.getValue());
					tokens.remove(i - 1);
				}
			}
		}

		Token[] tokenArray = new Token[tokens
				.size()];
		for (int i = 0; i < tokens.size(); i++) {
			tokenArray[i] = tokens.get(i);
		}
		return tokenArray;
	}

	private void createEquation(ArrayList<Token> tokens,
			int splitPosition) {

		// Splitposition wird immer ein Operator sein

		int leftEquationTokenAmount = splitPosition;
		int rightEquationTokenAmount = tokens.size()
				- splitPosition - 2;
		// linken u rechten Term füllen
		Token[] leftEquationTokens = new Token[leftEquationTokenAmount];
		Token[] rightEquationTokens = new Token[rightEquationTokenAmount];

		for (int i = 0; i < tokens.size(); i++) {
			if (i == splitPosition
					|| i == splitPosition + 1)
				continue;
			else if (i < splitPosition)
				leftEquationTokens[i] = tokens.get(i);
			else {
				rightEquationTokens[i - 2
						- splitPosition] = tokens.get(i);
			}
		}

		Calculation leftEquation = new Calculation(
				leftEquationTokens);
		Calculation rightEquation = new Calculation(
				rightEquationTokens);
		compare(leftEquation, rightEquation);
	}

	private void compare(Calculation a,
			Calculation b) {
		if (a.getResult() == b.getResult())
			System.out.println("TRUE");
		else
			System.out.println("FALSE");
	}
}