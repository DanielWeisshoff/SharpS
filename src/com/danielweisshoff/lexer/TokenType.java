package com.danielweisshoff.lexer;

public enum TokenType {
	//arithmetic
	ADD, SUB, MUL, DIV, MOD,
	//boolean
	COMPARISON, NOT, AND, OR, EQUAL,
	//
	IDENTIFIER, STRING,
	//NUMBER REPRESENTATION
	INTEGER, FLOATING_POINT,
	//
	O_ROUND_BRACKET, C_ROUND_BRACKET, COLON, DOT, COMMA,
	//KEYWORDS
	KW_INT, KW_IF, KW_ELSE, KW_ELIF, KW_FNC, KW_WHILE, KW_FOR, KW_DO,
	//Other shit
	COMMENT, NEWLINE, TAB, EOF,
}