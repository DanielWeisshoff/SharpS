package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;

public class Else {

    public static BlockNode parse(Parser p) {
        p.assume(TokenType.KW_ELSE, "Keyword ELSE missing");
        p.assume(TokenType.COLON, "unknown syntax for else statement");

        BlockNode bn = Block.parse(p, "else-body");
        return bn;
    }
}
