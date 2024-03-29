package com.danielweisshoff.lexer;

public enum TokenType {
    ADD, SUB, MUL, DIV,
    COMPARISON, NOT, ASSIGN,
    IDENTIFIER, KEYWORD, STRING, NUMBER, FLOAT, EOF,
    O_ROUND_BRACKET, C_ROUND_BRACKET,
    COLON, DOT, COMMA, COMMENT,
    NEWLINE, TAB,

}
//LESSTHAN, MORETHAN, EQUALS, LESSOREQUAL, MOREOREQUAL, NOTEQUAL,
// <   <=
// >  <=
// =  ==
// !  !=