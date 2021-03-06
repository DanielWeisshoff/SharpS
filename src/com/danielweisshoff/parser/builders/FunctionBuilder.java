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

    private static int entryCounter;

    public static EntryNode build(Parser p) {
        if (p.is("ntr"))
            return buildEntry(p);
        else if (p.is("con"))
            return buildConstructor(p);
        else
            return buildFunction(p);
    }

    private static EntryNode buildEntry(Parser p) {
        String functionName;

        if (p.next().type() == TokenType.IDENTIFIER) {
            p.advance();
            functionName = p.currentToken.getValue();
        } else
            functionName = "entry" + entryCounter++;

        p.advance();
        ParameterBuilder.buildParameters(p);
        if (!p.is(TokenType.COLON))
            new PError("Falsches Format");

        Logger.log("Entry '" + functionName + "' erkannt");

        EntryNode functionRoot = new EntryNode(functionName);
        p.currentClass.addEntry(functionRoot);

        p.manager.newScope(functionName);

        return functionRoot;
    }

    private static EntryNode buildConstructor(Parser p) {
        String functionName = "constructor";

        p.advance();
        ParameterBuilder.buildParameters(p);
        if (!p.is(TokenType.COLON))
            new PError("Falsches Format");

        Logger.log("Konstruktor '" + functionName + "' erkannt ");

        p.manager.newScope(functionName);

        return new EntryNode(functionName);
    }

    private static EntryNode buildFunction(Parser p) {
        p.advance();
        String functionName = p.currentToken.getValue();
        p.advance();
        ParameterBuilder.buildParameters(p);
        if (!p.is(TokenType.COLON))
            new PError("Body Declarator fehlt");

        Logger.log("Funktion '" + functionName + "' erkannt ");

        EntryNode function = new EntryNode(functionName);
        Function f = new Function(function);
        Parser.methods.put(functionName, f);

        p.manager.newScope(functionName);

        return function;
    }
}
