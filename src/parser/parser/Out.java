package parser.parser;

import java.util.ArrayList;

import lexer.Token;
import lexer.TokenType;
import parser.nodesystem.node.diverse.OutNode;

public class Out {
    public static OutNode parse(Parser p) {
        // KW_OUT (ID|STRING|NUMBER)*
        p.eat(TokenType.KW_OUT);

        ArrayList<Token> tokens = new ArrayList<>();
        do {
            tokens.add(p.curToken);
            p.eat();
        } while (!p.is(TokenType.EOF) && !p.is(TokenType.NEWLINE));

        return new OutNode(tokens);
    }
}
