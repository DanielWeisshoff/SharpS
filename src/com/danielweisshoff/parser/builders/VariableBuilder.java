package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Error;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Variable;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class VariableBuilder {


    public static Variable initializeVariable(Parser p) {
        if (p.currentToken.type() != TokenType.IDENTIFIER)
            new Error("Fehler beim Initialisieren einer Variable");
        String varName = p.currentToken.getValue();

        p.advance();

        Variable v;
        if (p.currentToken.type() == TokenType.ASSIGN) {
            v = declareVariable(varName, p);
            Logger.log("Variable initialisiert");
        } else {
            v = new Variable(varName, new Data<>(0, DataType.DOUBLE));
            Logger.log("Variable deklariert");
        }
        return v;
    }

    private static Variable declareVariable(String varName, Parser p) {
        p.advance();
        Node expr = ExpressionBuilder.buildExpression(p);
        Data<?> result = expr.execute();

        return new Variable(varName, result);
    }

    public static void assignVariable(Parser p) {
        if (p.next().type() != TokenType.ASSIGN) {
            ExpressionBuilder.buildExpression(p);
            return;
        }
        String varName = p.currentToken.getValue();
        if (!Parser.variables.containsKey(varName))
            new Error("Variable existiert nicht");

        p.advance();
        p.advance();
        Node expr = ExpressionBuilder.buildExpression(p);
        Data<?> result = expr.execute();
        Parser.variables.put(varName, result);
        Logger.log("Variablenwert verändert");
    }
}
