package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.BlockNode;

public class Else {

    public static BlockNode parse(Parser p) {
        p.assume(TokenType.KW_ELSE, "Keyword ELSE missing");
        p.assume(TokenType.COLON, "unknown syntax for else statement");

        BlockNode bn = Block.parse(p, "else-body");
        return bn;
    }
}
