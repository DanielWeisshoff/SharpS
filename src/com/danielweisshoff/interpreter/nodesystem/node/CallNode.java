package com.danielweisshoff.interpreter.nodesystem.node;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.DataType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;

/* TODO
 *  - Nachschauen ob Funktion existiert in FunctionBuilder verschieben
 *  - Nicht mehr namen, sondern ID benutzen
 */

/**
 * Calls the defined function
 */
public class CallNode extends Node {
    private final String name;

    public CallNode(String name) {
        super(null, null);
        this.name = name;
    }

    @Override
    public Data<?> execute() {
        Data<?> data = new Data<>(null, DataType.NULL);

        if (BuiltInFunction.builtInFunctions.containsKey(name)) {
            data = BuiltInFunction.builtInFunctions.get(name).call();
            Logger.log("BuiltInFunktion " + name + " wird aufgerufen");
        } else if (Parser.methods.containsKey(name)) {
            data = Parser.methods.get(name).getNode().execute();
            Logger.log("Funktion " + name + " wird aufgerufen");
        }

        return data;
    }
}
