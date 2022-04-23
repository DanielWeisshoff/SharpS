package com.danielweisshoff.lexer;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {

	public static String VERSION = "V 0.8.1";

	private final HashMap<Character, TokenType> tokenMap = new HashMap<>();
	private String text;

	private int charIndex = -1;
	private char currentChar;
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

	//test
	public Token[] nextLine() {
		ArrayList<Token> tokens = new ArrayList<>();

		while (charIndex < text.length() && currentChar != '\n') {
			if (tokenMap.containsKey(currentChar)) {
				tokens.add(new Token(tokenMap.get(currentChar), ""));
				advance();
			} else if (Character.isAlphabetic(currentChar)) {
				tokens.add(buildIdentifierOrKeywordToken());
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
				token = buildIdentifierOrKeywordToken();
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
	private Token buildIdentifierOrKeywordToken() {
		int start = charIndex;
		advance();
		while (charIndex < text.length()) {
			if (Character.isLetterOrDigit(currentChar)) {
				advance();
			} else
				break;
		}
		String subString = text.substring(start, charIndex);
		return switch (subString) {
		//PRIMITIVES
		case "bte" -> new Token(TokenType.KW_BYTE, null);
		case "sht" -> new Token(TokenType.KW_SHORT, null);
		case "int" -> new Token(TokenType.KW_INT, null);
		case "lng" -> new Token(TokenType.KW_LONG, null);
		case "flt" -> new Token(TokenType.KW_FLOAT, null);
		case "dbl" -> new Token(TokenType.KW_DOUBLE, null);
		//
		case "if" -> new Token(TokenType.KW_IF, null);
		case "else" -> new Token(TokenType.KW_ELSE, null);
		case "elif" -> new Token(TokenType.KW_ELIF, null);
		case "fnc" -> new Token(TokenType.KW_FNC, null);
		case "while" -> new Token(TokenType.KW_WHILE, null);
		case "for" -> new Token(TokenType.KW_FOR, null);
		case "do" -> new Token(TokenType.KW_DO, null);
		default -> new Token(TokenType.IDENTIFIER, subString);
		};
	}

	// "int", "if", "else", "elif", "fnc", "while", "for", "do" 
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
			return new Token(TokenType.FLOATING_POINT, text.substring(start, charIndex));
		return new Token(TokenType.INTEGER, text.substring(start, charIndex));
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
		tokenMap.put('+', TokenType.PLUS);
		tokenMap.put('-', TokenType.MINUS);
		tokenMap.put('*', TokenType.STAR);
		tokenMap.put('/', TokenType.SLASH);
		tokenMap.put('(', TokenType.O_ROUND_BRACKET);
		tokenMap.put(')', TokenType.C_ROUND_BRACKET);
		tokenMap.put('\n', TokenType.NEWLINE);
		tokenMap.put('.', TokenType.DOT);
		tokenMap.put(',', TokenType.COMMA);
		tokenMap.put(':', TokenType.COLON);
		tokenMap.put('%', TokenType.PERCENT);
		tokenMap.put('&', TokenType.AND);
		tokenMap.put('|', TokenType.OR);
	}
}
