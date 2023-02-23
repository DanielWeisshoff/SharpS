package parser.parser;

import lexer.Token;
import lexer.TokenType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.logic.conditions.ConditionNode;
import parser.nodesystem.node.logic.conditions.EqualNode;
import parser.nodesystem.node.logic.conditions.FalseNode;
import parser.nodesystem.node.logic.conditions.LessEqualNode;
import parser.nodesystem.node.logic.conditions.LessNode;
import parser.nodesystem.node.logic.conditions.MoreEqualNode;
import parser.nodesystem.node.logic.conditions.MoreNode;
import parser.nodesystem.node.logic.conditions.NotEqualNode;
import parser.nodesystem.node.logic.conditions.TrueNode;
import parser.parser.arithmetic.Expression;

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
