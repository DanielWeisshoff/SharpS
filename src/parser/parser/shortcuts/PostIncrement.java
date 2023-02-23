package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PostIncrementNode;
import parser.parser.Parser;

public class PostIncrement {

    public static AssignNode parse(Parser p, boolean isStandalone) {
        // ID + +
        String varName = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "post-increment-assignment var missing");

        p.assume(TokenType.PLUS, "Incrementor + missing");
        p.assume(TokenType.PLUS, "Incrementor + missing");

        PostIncrementNode lin = new PostIncrementNode(varName);

        //if (isStandalone)
        //p.addInstruction(lin);
        return lin;
    }
}
