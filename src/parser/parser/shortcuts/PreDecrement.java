package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PreDecrementNode;
import parser.parser.Parser;

public class PreDecrement {

    public static AssignNode parse(Parser p) {
        // - - ID
        p.eat(TokenType.MINUS);
        p.eat(TokenType.MINUS);

        String varName = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        return new PreDecrementNode(varName);
    }
}
