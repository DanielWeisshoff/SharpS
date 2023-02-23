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
        p.advance();

        p.assume(TokenType.O_BLOCK_BRACKET, "[ missing");
        NumberNode index = Expression.parse(p);
        p.assume(TokenType.C_BLOCK_BRACKET, "] missing");

        ArrGetFieldNode agfn = new ArrGetFieldNode(name, index);
        return agfn;
    }
}
