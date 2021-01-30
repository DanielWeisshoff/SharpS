package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Error;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Variable;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class VariableBuilder {


    public static Variable initializeVariable(Parser p) {
        String varName = "";
        if (p.currentToken.type() == TokenType.IDENTIFIER)
            varName = p.currentToken.getValue();
        else
            new Error("Fehler beim Initialisieren einer Variable");

        p.advance();
        Variable v;
        if (p.currentToken.type() == TokenType.ASSIGN) {
            p.advance();
            Node expr = ExpressionBuilder.buildExpression(p);
            Data<?> result = expr.execute();
            v = new Variable(varName, result);
            System.out.println("Variable initialisiert");
        } else {
            v = new Variable(varName, new Data<>(0, DataType.DOUBLE));
            System.out.println("Variable deklariert");
        }
        p.nextLine();
        return v;
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
        System.out.println("Variablenwert ver�ndert");
        p.nextLine();
    }
}
