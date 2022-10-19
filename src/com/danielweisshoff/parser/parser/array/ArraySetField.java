package com.danielweisshoff.parser.parser.array;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.ArrSetFieldNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.parser.arithmetic.Expression;

public class ArraySetField {

    public static Node parse(Parser p) {
        // ID [ EXPR ] = EXPR
        String name = p.curToken.value;
        p.advance();

        p.assume(TokenType.O_BLOCK_BRACKET, "[ missing");
        NumberNode index = Expression.parse(p);
        p.assume(TokenType.C_BLOCK_BRACKET, "] missing");

        p.assume(TokenType.EQUAL, "= missing");
        NumberNode value = Expression.parse(p);

        ArrSetFieldNode asfn = new ArrSetFieldNode(name, index, value);
        //p.addInstruction(asfn);

        return null;
    }
}