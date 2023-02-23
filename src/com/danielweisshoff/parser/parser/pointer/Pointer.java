package com.danielweisshoff.parser.parser.pointer;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.data.var.pointer.PointerNode;
import com.danielweisshoff.parser.parser.Parser;

public class Pointer {

    public static PointerNode parse(Parser p) {
        // * ID
        p.assume(TokenType.STAR, "pointer * missing");
        String name = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "pointer name missing");

        //TODO adresse fuer nullptr anlegen
        return new PointerNode(name, null);
    }
}
