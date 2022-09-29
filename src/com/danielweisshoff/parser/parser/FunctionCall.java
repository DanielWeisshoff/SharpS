package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.CallNode;

public class FunctionCall {

    private static Parser p;

    public static CallNode parse(Parser p) {
        FunctionCall.p = p;

        String name = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "Functionname missing");

        p.assume(TokenType.O_ROUND_BRACKET, "Parameterlist not opened");

        String params = parseParameters();

        p.assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");

        CallNode cn = new CallNode(name);
        //p.addInstruction(cn);

        return cn;
    }

    private static String parseParameters() {
        String params = "";

        while (!p.is(TokenType.C_ROUND_BRACKET)) {
            if (p.is(TokenType.INTEGER) || p.is(TokenType.FLOATING_POINT) || p.is(TokenType.IDENTIFIER)) {
                params += p.curToken.value;
                p.advance();
                if (p.is(TokenType.COMMA)) {
                    params += ", ";
                    p.advance();
                } else
                    break;
            }
        }
        return params;
    }
}
