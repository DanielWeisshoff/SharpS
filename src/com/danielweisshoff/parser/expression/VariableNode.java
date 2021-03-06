package com.danielweisshoff.parser.expression;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 *   - Execute ist hier unnötig -> Umweg finden
 */

import com.danielweisshoff.interpreter.nodesystem.node.Node;

public class VariableNode extends ExpressionNode {

    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public double execute() {
       /* double data = 0;
        if (Parser.variables.containsKey(name)) {
            data = Parser.variables.get(name).toDouble();
        } else if (BuiltInVariable.builtInVariables.containsKey(name)) {
            data = BuiltInVariable.builtInVariables.get(name).getData().toDouble();
        }
        System.out.println("data: " + data);
        return data;*/

        return 0;
    }

    @Override
    public Node toNode() {
        return new com.danielweisshoff.interpreter.nodesystem.node.data.VariableNode(name);
    }
}
