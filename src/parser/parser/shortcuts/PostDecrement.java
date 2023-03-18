package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PostDecrementNode;
import parser.parser.Parser;

public class PostDecrement {

    public static AssignNode parse(Parser p) {
        // ID - -
        String varName = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        p.eat(TokenType.MINUS);
        p.eat(TokenType.MINUS);

        return new PostDecrementNode(varName);
    }
}
