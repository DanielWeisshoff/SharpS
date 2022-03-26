package com.danielweisshoff.lexer;

public enum TokenType {
	//arithmetic
	ADD, SUB, MUL, DIV, MOD,
	//boolean
	COMPARISON, NOT, AND, OR,
	//
	EQUAL, IDENTIFIER, KEYWORD, STRING, NUMBER, FLOAT, EOF, O_ROUND_BRACKET, C_ROUND_BRACKET, COLON, DOT, COMMA,
	COMMENT, NEWLINE, TAB,

}