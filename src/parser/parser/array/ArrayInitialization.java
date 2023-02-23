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
