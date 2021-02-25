package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.node.InitNode;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;

/* TODO
 *  - Anstatt direkt Variablen zu erstellen, sollten Assign-nodes erstellt werden,
 *    welche erst bei Programmausführung Variablen anlegen
 */
public class VariableBuilder {

    public static Node initializeVariable(Parser p) {
        if (p.currentToken.type() != TokenType.IDENTIFIER)
            new PError("Fehler beim Initialisieren einer Variable");
        String varName = p.currentToken.getValue();

        p.advance();

        Node n;
        if (p.currentToken.type() == TokenType.ASSIGN) {
            n = initializeVariable(varName, p);
            Logger.log("Variable initialisiert");
        } else {
            n = new InitNode(varName);
            Logger.log("Variable deklariert");
        }
        return n;
    }

    private static Node initializeVariable(String varName, Parser p) {
        p.advance();
        Node expr = ExpressionBuilder.buildExpression(p);

        return new InitNode(varName, expr);
    }

    public static void assignVariable(Parser p) {
        if (p.next().type() != TokenType.ASSIGN) {
            ExpressionBuilder.buildExpression(p);
            return;
        }
        String varName = p.currentToken.getValue();
        if (!Parser.variables.containsKey(varName))
            new PError("Variable existiert nicht");

        p.advance();
        p.advance();
        Node expr = ExpressionBuilder.buildExpression(p);
        Data<?> result = expr.execute();
        Parser.variables.put(varName, result);
        Logger.log("Variablenwert verändert");
    }
}
