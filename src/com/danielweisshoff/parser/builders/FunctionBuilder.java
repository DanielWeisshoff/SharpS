package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.nodesystem.node.EntryNode;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.container.Function;

/* TODO
 *  - Da Methoden unveränderbar sind und nicht erzeugt / gelöscht werden können,
 *   sollten sie direkt mit IDs aufgerufen werden. Dazu wird hier eine ArrayList<String,int> angegeben um
 *   eingegebene Methodennamen zu vergleichen und anschließend die daraus entstehende id anstatt des
 *   Namens in der CallNode eintragen.
 *  - Nach bearbeitung die Todolist in VariableBuilder kopieren
 */
public class FunctionBuilder {

    public static EntryNode build(Parser p) {
        boolean isEntry = false;
        boolean isConstructor = false;
        String functionName;
        if (p.currentToken.getValue().equals("ntr")) {
            return buildEntry(p);
        } else if (p.currentToken.getValue().equals("con")) {
            return buildConstructor(p);
        } else {
            return buildFunction(p);
        }
    }

    private static EntryNode buildFunction(Parser p) {
        p.advance();
        String functionName = p.currentToken.getValue();
        p.advance();
        int i = ParameterBuilder.buildParameters(p);
        if (!p.is(TokenType.COLON))
            new PError("Body Deklarator fehlt");

        EntryNode function = new EntryNode(functionName);

        Logger.log("Funktion '" + functionName + "' erkannt ");

        //Eintragen einer normalen Funktion
        Function f = new Function(function);
        Parser.methods.put(functionName, f);

        return function;
    }

    private static EntryNode buildConstructor(Parser p) {
        String functionName = "constructor";

        p.advance();
        if (!p.are(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new PError("Falsches Format");

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

        p.advance();
        if (!p.are(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new PError("Falsches Format");

        EntryNode functionRoot = new EntryNode(functionName);
        p.currentClass.addEntry(functionRoot);
        Logger.log("Entry '" + functionName + "' erkannt");

        return functionRoot;
    }
}
