package parser.parser;

import java.util.ArrayList;

import lexer.Token;
import lexer.TokenType;
import parser.nodesystem.node.OutNode;

public class Out {
    public static OutNode parse(Parser p) {
        // KW_OUT (ID|STRING|NUMBER)*
        p.assume(TokenType.KW_OUT, "kw 'OUT' missing");

        ArrayList<Token> tokens = new ArrayList<>();
        do {
            tokens.add(p.curToken);
            p.advance();
        } while (!p.is(TokenType.EOF) && !p.is(TokenType.NEWLINE));

        OutNode on = new OutNode(tokens);
        //p.addInstruction(on);
        return on;
    }
}
