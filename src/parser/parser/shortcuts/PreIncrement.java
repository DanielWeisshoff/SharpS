package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PreIncrementNode;
import parser.parser.Parser;

public class PreIncrement {

    public static AssignNode parse(Parser p) {
        // + + ID
        p.eat(TokenType.PLUS);
        p.eat(TokenType.PLUS);

        String varName = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        return new PreIncrementNode(varName);
    }
}
