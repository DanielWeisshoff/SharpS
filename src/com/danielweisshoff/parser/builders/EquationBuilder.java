package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.nodesystem.node.EquationNode;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;

public class EquationBuilder {

    public static Node buildEquation(Parser p, Node leftExpression) {
        Token compareType = p.currentToken;
        p.advance();
        Node rightExpression = ExpressionBuilder.buildExpression(p);
        Logger.log("Gleichung erstellt");
        return new EquationNode(leftExpression, compareType.getValue(), rightExpression);
    }
}
