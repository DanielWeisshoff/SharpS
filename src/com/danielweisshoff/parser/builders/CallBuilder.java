package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Error;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.CallNode;

/* TODO
 * - Parameter sollen m�glich sein
 * - Es soll geschaut werden, ob die Parameter richtig sind
 * - Falls der R�ckgabewert gespeichert wird soll geschaut werden,
 *   ob R�ckgabe und Zieladresse vom gleichen Datentypen sind
 */
public class CallBuilder {

    public static CallNode buildCall(Parser p) {
        String name = p.currentToken.getValue();

        if (!p.compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET))
            new Error("Falsche Funktionsstruktur");

        if (!BuiltInFunction.builtInFunctions.containsKey(name))
            if (!Parser.methods.containsKey(name))
                new Error("Methode " + name + " existiert nicht");

        Logger.log("Funktionsaufruf " + name + " wurde erkannt");

        CallNode call = new CallNode(name);
        return call;
    }
}