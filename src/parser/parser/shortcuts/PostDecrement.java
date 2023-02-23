package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PostDecrementNode;
import parser.parser.Parser;

public class PostDecrement {

    public static AssignNode parse(Parser p, boolean isStandalone) {
        // ID - -
        String varName = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "post-decrement-assignment var missing");

        p.assume(TokenType.MINUS, "Decrementor - missing");
        p.assume(TokenType.MINUS, "Decrementor - missing");

        System.out.println("currently at: " + p.curToken.type());
        PostDecrementNode ldn = new PostDecrementNode(varName);

        //if (isStandalone)
        // p.addInstruction(ldn);
        return ldn;
    }
}
