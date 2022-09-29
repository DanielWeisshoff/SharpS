package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.IfNode;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

public class If {

    public static IfNode parse(Parser p) {
        p.assume(TokenType.KW_IF, "Keyword IF missing");
        p.assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");

        ConditionNode condition = Bool.parse(p);

        p.assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
        p.assume(TokenType.COLON, "if-block missing");

        IfNode in = new IfNode(condition);

        in.block = Block.parse(p, "if-body");

        if (p.is(TokenType.KW_ELIF)) {
            in.elseBlock = new BlockNode(p.getScope(), "if-body");
            in.elseBlock.add(Elif.parse(p));
        } else if (p.is(TokenType.KW_ELSE))
            in.elseBlock = Else.parse(p);

        return in;
    }
}
