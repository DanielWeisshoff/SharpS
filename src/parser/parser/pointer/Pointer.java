package parser.parser.pointer;

import lexer.TokenType;
import parser.nodesystem.node.data.var.pointer.PointerNode;
import parser.parser.Parser;

public class Pointer {

    public static PointerNode parse(Parser p) {
        // * ID
        p.eat(TokenType.STAR);
        String name = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        //TODO adresse fuer nullptr anlegen
        return new PointerNode(name, null);
    }
}
