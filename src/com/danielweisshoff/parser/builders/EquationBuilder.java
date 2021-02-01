package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.EquationNode;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class EquationBuilder {

    public static Node buildEquation(Parser p, Node leftExpression) {
        Token compareType = p.currentToken;
        p.advance();
        Node rightExpression = ExpressionBuilder.buildExpression(p);
        Logger.log("Gleichung erstellt");
        return new EquationNode(leftExpression, compareType.getValue(), rightExpression);
    }
}
