package com.danielweisshoff.lexer;

import java.util.ArrayList;

import com.danielweisshoff.parser.PError;

public class Token {

	private final TokenType type;
	public String value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	public static boolean areSameCategoryOP(Token t1, Token t2) {
		return t1.isLineOP() && t2.isLineOP() || t1.isDotOP() && t2.isDotOP();
	}

	public static Token[] toArray(ArrayList<Token> list) {
		Token[] tokens = new Token[list.size()];
		return list.toArray(tokens);
	}

	public String getDescription() {
		if (value != null)
			return ("[" + type + ", " + value + "]");
		else
			return ("[" + type + "]");
	}

	public TokenType type() {
		return type;
	}

	public boolean isLineOP() {
		return type == TokenType.ADD || type == TokenType.SUB;
	}

	public boolean isDotOP() {
		return type == TokenType.MUL || type == TokenType.DIV;
	}

	public boolean isOP() {
		return isLineOP() || isDotOP();
	}

	public boolean isEOF() {
		return type == TokenType.EOF;
	}

	public boolean isNumeric() {
		return type == TokenType.NUMBER || type == TokenType.FLOAT;
	}

	//TODO needs constant updates
	public boolean isPrimitive() {

		return switch (type) {
		case KW_INT -> true;
		default -> unknownPrimitiveError();
		};
	}

	//TODO just helper for now
	private boolean unknownPrimitiveError() {
		new PError("Unknown primitive '" + type + "'");
		return false;
	}
}
