package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PreIncrementNode;
import parser.parser.Parser;

public class PreIncrement {

    public static AssignNode parse(Parser p, boolean isStandalone) {
        // + + ID
        p.assume(TokenType.PLUS, "Expected + for incrementing");
        p.assume(TokenType.PLUS, "Expected + for incrementing");

        String varName = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "pre-increment-assignment var missing");

        PreIncrementNode in = new PreIncrementNode(varName);

        // if (isStandalone)
        //    p.addInstruction(in);
        return in;
    }
}
