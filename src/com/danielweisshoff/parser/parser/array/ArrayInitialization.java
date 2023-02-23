package com.danielweisshoff.parser.parser.array;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.array.ArrInitNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.parser.arithmetic.Expression;

public class ArrayInitialization {

    // KW ID [ EXPR ]
    public static ArrInitNode parse(Parser p) {
        TokenType keyword = p.curToken.type();
        p.advance();

        String name = p.curToken.value;
        p.advance();

        p.assume(TokenType.O_BLOCK_BRACKET, "missing [ for index");
        NumberNode size = Expression.parse(p);
        p.assume(TokenType.C_BLOCK_BRACKET, "missing ] for index");

        DataType dataType = p.getPrimitiveType(keyword);
        ArrInitNode an = new ArrInitNode(name, dataType, size);
        // p.addInstruction(an);

        return an;
    }
}
