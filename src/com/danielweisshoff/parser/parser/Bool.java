package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.logic.BooleanAndNode;
import com.danielweisshoff.parser.nodesystem.node.logic.BooleanOrNode;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

public class Bool {

    public static ConditionNode parse(Parser p) {
        ConditionNode left = Predicate.parse(p);

        ConditionNode cn = null;

        while (p.is(TokenType.AND) || p.is(TokenType.PIPE)) {
            // PREDICATE && PREDICATE
            if (p.is(TokenType.AND) && p.next(TokenType.AND)) {
                p.advance(2);
                cn = new BooleanAndNode();
            }
            //PREDICATE || PREDICATE
            else if (p.is(TokenType.PIPE) && p.next(TokenType.PIPE)) {
                p.advance(2);
                cn = new BooleanOrNode();
            }

            ConditionNode right = Predicate.parse(p);
            cn.left = left;
            cn.right = right;
            left = cn;
        }
        return left;
    }
}
