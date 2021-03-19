package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Parser;

public class ParameterBuilder {

    /*TODO
     *erstmals nur placeholder
     */
    public static void buildParameters(Parser p) {
        while (p.currentToken.type() != TokenType.C_ROUND_BRACKET)
            p.advance();
        p.advance();
    }
}
