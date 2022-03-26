package com.danielweisshoff.lexer;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

	public static String VERSION = "V 0.8.1";
	private final HashMap<Character, TokenType> tokenMap = new HashMap<>();
	private int charIndex = -1;
	private char currentChar;
	private String text;
	private boolean hasNext = true; //if the pointer is at the end or not

	public Lexer(String text) {
		this.text = text;
		initializeSingleCharTokens();
		advance();
	}

	private void advance() {
		charIndex++;
		if (charIndex < text.length())
			currentChar = text.charAt(charIndex);
	}

	public Token[] nextLine() {
		ArrayList<Token> tokens = new ArrayList<>();

		//skip empty lines
		if (currentChar == '\n')
			return nextLine();

		while (charIndex < text.length() && currentChar != '\n') {
			if (tokenMap.containsKey(currentChar)) {
				tokens.add(new Token(tokenMap.get(currentChar), ""));
				advance();
			} else if (Character.isAlphabetic(currentChar)) {
				tokens.add(buildIdentifierToken());
			} else if (Character.isDigit(currentChar)) {
				tokens.add(buildNumberToken());
			} else if (currentChar == ' ' || currentChar == '\t') {
				Token t = buildTabToken();
				if (t != null)
					tokens.add(t);
			} else {
				switch (currentChar) {
				case '"' -> tokens.add(buildStringToken());
				case '#' -> skipComment();
				case '=' -> tokens.add(buildComparisonToken('='));
				case '<' -> tokens.add(buildComparisonToken('<'));
				case '>' -> tokens.add(buildComparisonToken('>'));
				case '!' -> tokens.add(buildComparisonToken('!'));
				default -> advance();
				}
			}
		}

		//Newline skippen
		advance();

		//check if file at end 
		if (charIndex >= text.length())
			hasNext = false;

		//tokens.add(new Token(TokenType.EOF, ""));
		return Token.toArray(tokens);
	}

	//In this case, the text is just a single line
	public Token[] tokenizeText() {
		return null;
	}

	//TODO outdated, needs checks from nextLine
	public Token nextToken() {
		if (charIndex >= text.length())
			return new Token(TokenType.EOF, "");

		Token token = null;
		while (token == null && charIndex < text.length()) {
			if (tokenMap.containsKey(currentChar)) {
				token = new Token(tokenMap.get(currentChar), "");
				advance();
			} else if (Character.isAlphabetic(currentChar)) {
				token = buildIdentifierToken();
			} else if (Character.isDigit(currentChar)) {
				token = buildNumberToken();
			} else {
				switch (currentChar) {
				case '"' -> token = (buildStringToken());
				case '#' -> skipComment();
				case '=' -> token = buildComparisonToken('=');
				case '<' -> token = buildComparisonToken('<');
				case '>' -> token = buildComparisonToken('>');
				case '!' -> token = buildComparisonToken('!');
				default -> advance();
				}
			}
		}
		return token;
	}

	public boolean hasNextLine() {
		return hasNext;
	}

	//
	// building stuff
	//
	private Token buildIdentifierToken() {
		int start = charIndex;
		advance();
		while (charIndex < text.length()) {
			if (Character.isLetterOrDigit(currentChar)) {
				advance();
			} else
				break;
		}
		String subString = text.substring(start, charIndex);
		for (String s : Keywords.keywords)
			if (subString.equals(s))
				return new Token(TokenType.KEYWORD, subString.toUpperCase());
		return new Token(TokenType.IDENTIFIER, subString);
	}

	private Token buildTabToken() {
		Token t = null;
		int whitespaceCount = 0;

		while (charIndex < text.length()) {
			if (currentChar == ' ')
				whitespaceCount++;
			else if (currentChar == '\t')
				whitespaceCount += 4;
			else
				break;
			advance();
		}
		if (whitespaceCount >= 4)
			t = new Token(TokenType.TAB, "" + (int) Math.floor(whitespaceCount / 4));
		return t;
	}

	private Token buildNumberToken() {
		int start = charIndex;
		boolean isFloat = false;
		advance();
		while (charIndex < text.length()) {
			if (Character.isDigit(currentChar) || currentChar == '.') {
				if (currentChar == '.')
					isFloat = true;
				advance();
			} else
				break;
		}
		if (isFloat)
			return new Token(TokenType.FLOAT, text.substring(start, charIndex));
		return new Token(TokenType.NUMBER, text.substring(start, charIndex));
	}

	private Token buildComparisonToken(char c) {
		advance();
		if (charIndex < text.length()) {
			if (currentChar == '=') {
				advance();
				return new Token(TokenType.COMPARISON, c + "=");
			}
		}
		return new Token(TokenType.EQUAL, "" + c);
	}

	private Token buildStringToken() {
		advance();
		int start = charIndex;

		while (charIndex < text.length()) {
			if (currentChar != '"') {
				advance();
			} else {
				advance();
				break;
			}
		}
		return new Token(TokenType.STRING, text.substring(start, charIndex - 1));
	}

	private void skipComment() {
		while (charIndex < text.length()) {
			if (currentChar != '\n') {
				advance();
			} else
				return;
		}
	}

	/*
	 * Hier können alle Einzeltokens eingetragen werden
	 */
	private void initializeSingleCharTokens() {
		tokenMap.put('+', TokenType.ADD);
		tokenMap.put('-', TokenType.SUB);
		tokenMap.put('*', TokenType.MUL);
		tokenMap.put('/', TokenType.DIV);
		tokenMap.put('(', TokenType.O_ROUND_BRACKET);
		tokenMap.put(')', TokenType.C_ROUND_BRACKET);
		tokenMap.put('\n', TokenType.NEWLINE);
		tokenMap.put('.', TokenType.DOT);
		tokenMap.put(',', TokenType.COMMA);
		tokenMap.put(':', TokenType.COLON);
		tokenMap.put('%', TokenType.MOD);
		tokenMap.put('&', TokenType.AND);
		tokenMap.put('|', TokenType.OR);
	}
}
