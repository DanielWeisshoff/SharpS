package com.danielweisshoff.lexer;

public class Token {

	private TokenType type;
	private String value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	public void print() {
		if (value != null)
			System.out.println(
					"[" + type + ", " + value + "]");
		else
			System.out.println("[" + type + "]");
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		// Error checking einfügen
		this.value = value;
	}

	public boolean isOP() {
		if (type == TokenType.ADD || type == TokenType.SUB
				|| type == TokenType.MUL
				|| type == TokenType.DIV)
			return true;
		return false;
	}

	/**
	 * True, wenn der Token ein + oder - Operator ist
	 * 
	 * @return
	 */
	public boolean isLineOP() {
		if (type == TokenType.ADD || type == TokenType.SUB)
			return true;
		return false;
	}

	/**
	 * True, wenn der Token ein * oder / Operator ist
	 * 
	 * @return
	 */
	public boolean isDotOP() {
		if (type == TokenType.MUL || type == TokenType.DIV)
			return true;
		return false;
	}

	public boolean isEOF() {
		if (type == TokenType.EOF)
			return true;
		return false;
	}

	public static boolean areSameCategoryOP(Token t1,
			Token t2) {
		return t1.isLineOP() && t2.isLineOP()
				|| t1.isDotOP() && t2.isDotOP();
	}

	public boolean isNumeric() {
		if (type == TokenType.NUMBER
				|| type == TokenType.FLOAT)
			return true;
		return false;
	}
}
