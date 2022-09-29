package com.danielweisshoff.parser.parser.array;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.ArrGetFieldNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.parser.arithmetic.Expression;

public class ArrayGetField {

    public static NumberNode parse(Parser p) {
        // ID [ EXPR ]
        String name = p.curToken.value;
        p.advance();

        p.assume(TokenType.O_BLOCK_BRACKET, "[ missing");
        NumberNode index = Expression.parse(p);
        p.assume(TokenType.C_BLOCK_BRACKET, "] missing");

        ArrGetFieldNode agfn = new ArrGetFieldNode(name, index);
        return agfn;
    }
}
