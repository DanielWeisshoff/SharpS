package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.BlockNode;
import parser.nodesystem.node.IfNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;

public class If {

    /** if ( BOOL ) : BLOCK */
    public static IfNode parse(Parser p) {

        p.assume(TokenType.KW_IF, "Keyword IF missing");
        p.assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");

        ConditionNode condition = Bool.parse(p);

        p.assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
        p.assume(TokenType.COLON, "if-block missing");

        IfNode in = new IfNode(condition);

        in.block = Block.parse(p, "if-body");

        if (p.is(TokenType.KW_ELIF)) {
            in.elseBlock = new BlockNode("if-body");
            in.elseBlock.add(Elif.parse(p));
        } else if (p.is(TokenType.KW_ELSE))
            in.elseBlock = Else.parse(p);

        return in;
    }
}
