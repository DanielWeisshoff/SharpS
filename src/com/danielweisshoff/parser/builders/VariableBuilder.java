package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.node.AssignNode;
import com.danielweisshoff.interpreter.nodesystem.node.InitNode;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.symboltable.DataType;
import com.danielweisshoff.parser.symboltable.ReturnType;

public class VariableBuilder {

    public static Node initializeVariable(Parser p) {
        if (!p.is(TokenType.IDENTIFIER))
            new PError("Fehler beim Initialisieren einer Variable");
        String varName = p.currentToken.getValue();

        p.advance();

        Node n;
        if (p.is(TokenType.ASSIGN)) {
            n = initializeVariable(varName, p);
            Logger.log("Variable initialisiert");
        } else {
            n = new InitNode(varName);
            Logger.log("Variable deklariert");
        }
        //Nur tewmporär
        Parser.variables.put(varName, new Data<>());

        p.manager.addToScope(varName, DataType.VARIABLE, ReturnType.INT);
        return n;
    }

    private static Node initializeVariable(String varName, Parser p) {
        p.advance();
        Node expr = ExpressionBuilder.buildExpression(p);

        return new InitNode(varName, expr);
    }

    public static AssignNode assignVariable(Parser p) {
        String varName = p.currentToken.getValue();
        if (!Parser.variables.containsKey(varName))
            new PError("Variable existiert nicht");

        p.advance(); //Varname
        p.advance(); //Gleichzeichen
        Node expr = ExpressionBuilder.buildExpression(p);
        Logger.log("Variablenwert verändert");
        return new AssignNode(varName, expr);
    }
}
