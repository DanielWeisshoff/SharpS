package com.danielweisshoff.parser.parser.pointer;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.parser.Parser;

public class Adress {

    public static String parse(Parser p) {
        // & ID
        p.assume(TokenType.AND, "adress & missing");
        String name = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "adress name missing");

        return name;
    }
}
