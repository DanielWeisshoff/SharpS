package parser.parser.array;

import lexer.TokenType;
import parser.nodesystem.DataType;
import parser.nodesystem.node.binaryoperations.NumberNode;
import parser.nodesystem.node.data.var.array.ArrInitNode;
import parser.parser.Parser;
import parser.parser.arithmetic.Expression;

public class ArrayInitialization {

    // KW ID [ EXPR ]
    public static ArrInitNode parse(Parser p) {
        TokenType keyword = p.curToken.type;
        p.eat();

        String name = p.curToken.value;
        p.eat();

        p.eat(TokenType.O_BLOCK_BRACKET);
        NumberNode size = Expression.parse(p);
        p.eat(TokenType.C_BLOCK_BRACKET);

        DataType dataType = p.getPrimitiveType(keyword);

        return new ArrInitNode(name, dataType, size);
    }
}
