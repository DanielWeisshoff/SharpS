package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.diverse.BlockNode;

public class Else {

    public static BlockNode parse(Parser p) {
        p.eat(TokenType.KW_ELSE);
        p.eat(TokenType.COLON);

        BlockNode bn = Block.parse(p, "else-body");
        return bn;
    }
}
