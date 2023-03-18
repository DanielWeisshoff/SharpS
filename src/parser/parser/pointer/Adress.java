package parser.parser.pointer;

import lexer.TokenType;
import parser.parser.Parser;

public class Adress {

    public static String parse(Parser p) {
        // & ID
        p.eat(TokenType.AND);
        String name = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        return name;
    }
}
