package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.interpreter.nodesystem.node.logic.*;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;

public class EquationBuilder {

    public static Node buildEquation(Parser p, Node leftExpression) {
        Token compareType = p.currentToken;
        p.advance();
        Node rightExpression = ExpressionBuilder.buildExpression(p);
        Logger.log("Gleichung erstellt");

        return switch (compareType.getValue()) {
            case "<" -> new LessNode(leftExpression, rightExpression);
            case "<=" -> new LessEqualNode(leftExpression, rightExpression);
            case ">" -> new MoreNode(leftExpression, rightExpression);
            case ">=" -> new MoreEqualNode(leftExpression, rightExpression);
            case "==" -> new EqualNode(leftExpression, rightExpression);
            case "!=" -> new NotEqualNode(leftExpression, rightExpression);
            default -> null;
        };
    }
}
