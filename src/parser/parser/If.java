package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.diverse.BlockNode;
import parser.nodesystem.node.diverse.IfNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;

public class If {

    /** if ( BOOL ) : BLOCK */
    public static IfNode parse(Parser p) {

        p.eat(TokenType.KW_IF);
        p.eat(TokenType.O_ROUND_BRACKET);

        ConditionNode condition = Bool.parse(p);

        p.eat(TokenType.C_ROUND_BRACKET);
        p.eat(TokenType.COLON);

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
