package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Error;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.CallNode;

public class CallBuilder {

    public static CallNode buildCall(Parser p) {
        String name = p.currentToken.getValue();

        if (!Parser.methods.containsKey(name))
            new Error("Methode " + name + " existiert nicht");

        if (p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET))
            System.out.println("Methode " + name + " wird aufgerufen");

        CallNode call = new CallNode(name);
        return null;
    }
}
