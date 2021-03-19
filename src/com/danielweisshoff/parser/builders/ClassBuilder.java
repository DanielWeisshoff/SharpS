package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Class;

public class ClassBuilder {
    public static Class buildClass(Parser p) {
        p.advance();
        if (!p.is(TokenType.IDENTIFIER))
            new PError("Klassenname fehlt");
        String className = p.currentToken.getValue();

        p.advance();
        if (!p.is(TokenType.COLON)) {
            new PError("Methodenstruktur falsch");
        }

        Logger.log("Klasse " + className + " erkannt");


        p.manager.toRoot();
        p.manager.newScope(className);
        return new Class(className);
    }
}
