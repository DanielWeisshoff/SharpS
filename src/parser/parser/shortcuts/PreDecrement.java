package parser.parser.shortcuts;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.shortcuts.PreDecrementNode;
import parser.parser.Parser;

public class PreDecrement {

    public static AssignNode parse(Parser p, boolean isStandalone) {
        // - - ID
        p.assume(TokenType.MINUS, "Expected - for decrementing");
        p.assume(TokenType.MINUS, "Expected - for decrementing");

        String varName = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "pre-decrement-assignment var missing");

        PreDecrementNode dn = new PreDecrementNode(varName);

        // if (isStandalone)
        //    p.addInstruction(dn);
        return dn;
    }
}
