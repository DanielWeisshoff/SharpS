package parser.parser.arithmetic;

import lexer.TokenType;
import parser.nodesystem.node.binaryoperations.BinaryAddNode;
import parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import parser.nodesystem.node.binaryoperations.BinarySubNode;
import parser.nodesystem.node.data.primitives.NumberNode;
import parser.parser.Parser;

public class Expression {
    public static NumberNode parse(Parser p) {
        NumberNode left = Term.parse(p);

        BinaryOperationNode op = null;

        while (p.curToken.isLineOP()) {

            if (p.is(TokenType.PLUS))
                op = new BinaryAddNode();
            else if (p.is(TokenType.MINUS))
                op = new BinarySubNode();

            p.eat();

            NumberNode right = Term.parse(p);
            op.left = left;
            op.right = right;
            left = op;
        }
        return left;
    }
}
