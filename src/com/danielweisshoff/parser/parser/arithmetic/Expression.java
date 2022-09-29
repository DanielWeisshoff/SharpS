package com.danielweisshoff.parser.parser.arithmetic;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryAddNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinarySubNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.parser.Parser;

public class Expression {
    public static NumberNode parse(Parser p) {
        NumberNode left = Term.parse(p);

        BinaryOperationNode op = null;

        while (p.curToken.isLineOP()) {

            if (p.curToken.type() == TokenType.PLUS)
                op = new BinaryAddNode();
            else if (p.curToken.type() == TokenType.MINUS)
                op = new BinarySubNode();

            p.advance();

            NumberNode right = Term.parse(p);
            op.left = left;
            op.right = right;
            left = op;
        }
        return left;
    }
}
