package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PostIncrementNode;
import parser.parser.Parser;

public class PostIncrement {

    public static AssignNode parse(Parser p) {
        // ID + +
        String varName = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        p.eat(TokenType.PLUS);
        p.eat(TokenType.PLUS);

        return new PostIncrementNode(varName);
    }
}
