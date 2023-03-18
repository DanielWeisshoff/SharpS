package parser.parser.arithmetic;

import lexer.TokenType;
import parser.nodesystem.node.binaryoperations.BinaryDivNode;
import parser.nodesystem.node.binaryoperations.BinaryModNode;
import parser.nodesystem.node.binaryoperations.BinaryMulNode;
import parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import parser.nodesystem.node.binaryoperations.NumberNode;
import parser.parser.Parser;

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

            p.eat();

            NumberNode right = Factor.parse(p);
            op.left = left;
            op.right = right;
            left = op;
        }
        return left;
    }
}
