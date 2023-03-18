package parser.parser.array;

import lexer.TokenType;
import parser.nodesystem.node.binaryoperations.NumberNode;
import parser.nodesystem.node.data.var.array.ArrGetFieldNode;
import parser.parser.Parser;
import parser.parser.arithmetic.Expression;

public class ArrayGetField {

    public static NumberNode parse(Parser p) {
        // ID [ EXPR ]
        String name = p.curToken.value;
        p.eat();

        p.eat(TokenType.O_BLOCK_BRACKET);
        NumberNode index = Expression.parse(p);
        p.eat(TokenType.C_BLOCK_BRACKET);

        ArrGetFieldNode agfn = new ArrGetFieldNode(name, index);
        return agfn;
    }
}
