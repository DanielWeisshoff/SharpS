package com.danielweisshoff.parser.parser.arithmetic;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryDivNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryModNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryMulNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.parser.Parser;

public class Term {

    public static NumberNode parse(Parser p) {
        NumberNode left = Factor.parse(p);

        BinaryOperationNode op = null;
        while (p.curToken.isDotOP() || p.is(TokenType.PERCENT)) {

            if (p.is(TokenType.STAR))
                op = new BinaryMulNode();
            else if (p.is(TokenType.SLASH))
                op = new BinaryDivNode();
            else if (p.is(TokenType.PERCENT))
                op = new BinaryModNode();

            p.advance();

            NumberNode right = Factor.parse(p);
            op.left = left;
            op.right = right;
            left = op;
        }
        return left;
    }
}
