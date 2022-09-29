package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;
import com.danielweisshoff.parser.parser.arithmetic.Expression;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.*;

public class Predicate {

    public static ConditionNode parse(Parser p) {
        switch (p.curToken.type()) {
        case O_ROUND_BRACKET:
            p.advance();
            ConditionNode n = Bool.parse(p);
            p.assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
            return n;
        case KW_TRUE:
            p.advance();
            return new TrueNode();
        case KW_FALSE:
            p.advance();
            return new FalseNode();
        default:
            Node leftExpr = Expression.parse(p);
            Token compareType = p.curToken;
            p.advance();
            Node rightExpr = Expression.parse(p);

            return switch (compareType.value) {
            case "<" -> new LessNode(leftExpr, rightExpr);
            case "<=" -> new LessEqualNode(leftExpr, rightExpr);
            case ">" -> new MoreNode(leftExpr, rightExpr);
            case ">=" -> new MoreEqualNode(leftExpr, rightExpr);
            case "==" -> new EqualNode(leftExpr, rightExpr);
            case "!=" -> new NotEqualNode(leftExpr, rightExpr);
            default -> null;
            };
        }
    }
}
