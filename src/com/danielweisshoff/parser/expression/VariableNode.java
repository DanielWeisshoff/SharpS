package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.builtin.BuiltInVariable;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.parser.Parser;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 */

/**
 * Holds the name of a variable and returns the stored data
 */
public class VariableNode extends ExpressionNode {

    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public double execute() {
        double data = 0;
        if (Parser.variables.containsKey(name)) {
            data = Parser.variables.get(name).toDouble();
        } else if (BuiltInVariable.builtInVariables.containsKey(name)) {
            data = BuiltInVariable.builtInVariables.get(name).getData().toDouble();
        }
        return data;
    }

    @Override
    public Node toNode() {
        return new com.danielweisshoff.interpreter.nodesystem.node.VariableNode(name);
    }
}
