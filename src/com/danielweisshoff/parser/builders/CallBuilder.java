package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.interpreter.nodesystem.node.CallNode;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;

/* TODO
 *  - Parameter sollen möglich sein
 *  - Es soll geschaut werden, ob die Parameter richtig sind
 *  - Falls der Rückgabewert gespeichert wird soll geschaut werden,
 *    ob Rückgabe und Zieladresse vom gleichen Datentypen sind
 */
public class CallBuilder {

    public static CallNode buildCall(Parser p) {
        String name = p.currentToken.getValue();
        p.advance();
        ParameterBuilder.buildParameters(p);

        if (!BuiltInFunction.builtInFunctions.containsKey(name))
            if (!Parser.methods.containsKey(name))
                new PError("Methode " + name + " existiert nicht");

        Logger.log("Funktionsaufruf " + name + " wurde erkannt");

        return new CallNode(name);
    }
}