package com.danielweisshoff.parser.parser.shortcuts;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PostIncrementNode;
import com.danielweisshoff.parser.parser.Parser;

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
