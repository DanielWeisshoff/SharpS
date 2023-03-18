package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.logic.BooleanAndNode;
import parser.nodesystem.node.logic.BooleanOrNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;

public class Bool {

    public static ConditionNode parse(Parser p) {
        ConditionNode left = Predicate.parse(p);

        ConditionNode cn = null;

        while (p.is(TokenType.AND) || p.is(TokenType.PIPE)) {
            // PREDICATE && PREDICATE
            if (p.is(TokenType.AND) && p.next(TokenType.AND)) {
                p.eat(2);
                cn = new BooleanAndNode();
            }
            //PREDICATE || PREDICATE
            else if (p.is(TokenType.PIPE) && p.next(TokenType.PIPE)) {
                p.eat(2);
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
