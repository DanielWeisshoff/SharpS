package com.danielweisshoff.lexer;

public enum TokenType {
    ADD, SUB, MUL, DIV,
    LESSTHAN, MORETHAN, EQUALS, LESSOREQUAL, MOREOREQUAL,ISSAME, NOTEQUAL, NOT, ASSIGN,
    IDENTIFIER, KEYWORD, STRING, NUMBER, FLOAT, EOF,
    O_ROUND_BRACKET, C_ROUND_BRACKET,
    COLON, DOT, COMMA,
    NEWLINE,

}

// <   <=
// >  <=
// =  ==
// !  !=