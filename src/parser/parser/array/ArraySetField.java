package parser.parser.array;

import lexer.TokenType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.data.primitives.NumberNode;
import parser.nodesystem.node.data.var.array.ArrSetFieldNode;
import parser.parser.Parser;
import parser.parser.arithmetic.Expression;

public class ArraySetField {

    public static Node parse(Parser p) {
        // ID [ EXPR ] = EXPR
        String name = p.curToken.value;
        p.eat();

        p.eat(TokenType.O_BLOCK_BRACKET);
        NumberNode index = Expression.parse(p);
        p.eat(TokenType.C_BLOCK_BRACKET);

        p.eat(TokenType.EQUAL);
        NumberNode value = Expression.parse(p);

        return new ArrSetFieldNode(name, index, value);
    }
}
