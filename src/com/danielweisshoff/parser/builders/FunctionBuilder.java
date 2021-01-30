package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
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
            isEntry = true;
            functionName = "entry";
        } else if (p.currentToken.getValue().equals("con")) {
            functionName = "constructor";
            isConstructor = true;
        } else {
            p.advance();
            functionName = p.currentToken.getValue();
        }
        if (!p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new Error("Falsches Format");

        p.advance();

        EntryNode functionRoot = new EntryNode(functionName);
        if (isEntry) {
            p.currentClass.addEntry(functionRoot);
            System.out.println("Entry '" + functionName + "' erkannt");
        } else {
            System.out.println("Funktion '" + functionName + "' erkannt ");
        }
        //Eintragen einer normalen Funktion
        if (!isEntry && !isConstructor) {
            Function f = new Function(functionRoot);
            Parser.methods.put(functionName, f);
        }

        p.nextLine();
        return functionRoot;
    }
}
