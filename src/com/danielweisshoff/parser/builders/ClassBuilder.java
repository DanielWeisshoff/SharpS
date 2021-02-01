package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Error;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Class;

public class ClassBuilder {
    public static Class buildClass(Parser p) {
        p.advance();
        if (p.currentToken.type() != TokenType.IDENTIFIER)
            new Error("Klassenname fehlt");
        String className = p.currentToken.getValue();

        if (!p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON)) {
            new Error("Methodenstruktur falsch");
        }

        Logger.log("Klasse " + className + " erkannt");
        return new Class(className);
    }
}
