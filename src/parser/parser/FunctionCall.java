package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.diverse.CallNode;

public class FunctionCall {

    public static CallNode parse(Parser p) {
        String name = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);
        p.eat(TokenType.O_ROUND_BRACKET);

        String params = parseParameters(p);

        p.eat(TokenType.C_ROUND_BRACKET);

        return new CallNode(name);
    }

    private static String parseParameters(Parser p) {
        String params = "";

        while (!p.is(TokenType.C_ROUND_BRACKET)) {
            if (p.is(TokenType.INTEGER) || p.is(TokenType.FLOATING_POINT) || p.is(TokenType.IDENTIFIER)) {
                params += p.curToken.value;
                p.eat();
                if (p.is(TokenType.COMMA)) {
                    params += ", ";
                    p.eat();
                } else
                    break;
            }
        }
        return params;
    }
}
