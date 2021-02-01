package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Error;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Function;
import com.danielweisshoff.parser.nodesystem.node.EntryNode;

public class FunctionBuilder {

    public static EntryNode buildFunction(Parser p) {
        boolean isEntry = false;
        boolean isConstructor = false;
        String functionName;
        if (p.currentToken.getValue().equals("ntr")) {
            return buildEntry(p);
        } else if (p.currentToken.getValue().equals("con")) {
            return buildConstructor(p);
        } else {
            return build(p);
        }
    }

    private static EntryNode build(Parser p) {
        p.advance();
        String functionName = p.currentToken.getValue();

        if (!p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new Error("Falsches Format");

        EntryNode function = new EntryNode(functionName);

        Logger.log("Funktion '" + functionName + "' erkannt ");

        //Eintragen einer normalen Funktion
        Function f = new Function(function);
        Parser.methods.put(functionName, f);

        return function;
    }

    private static EntryNode buildConstructor(Parser p) {
        String functionName = "constructor";

        if (!p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new Error("Falsches Format");

        EntryNode functionRoot = new EntryNode(functionName);

        Logger.log("Konstruktor '" + functionName + "' erkannt ");

        return functionRoot;
    }

    private static EntryNode buildEntry(Parser p) {

        String functionName = "entry";

        if (p.next().type() == TokenType.IDENTIFIER) {
            p.advance();
            functionName = p.currentToken.getValue();
        }

        if (!p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new Error("Falsches Format");

        EntryNode functionRoot = new EntryNode(functionName);
        p.currentClass.addEntry(functionRoot);
        Logger.log("Entry '" + functionName + "' erkannt");

        return functionRoot;
    }
}
