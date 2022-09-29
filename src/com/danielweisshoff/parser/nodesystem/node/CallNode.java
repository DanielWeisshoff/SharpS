package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.interpreter.builtin.functions.BuiltInFunction;
import com.danielweisshoff.parser.nodesystem.Data;

/**
 * Calls the defined function
 */
public class CallNode extends Node {
    private final String name;

    public CallNode(String name) {
        super(null, null, NodeType.CALL_NODE);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Data run() {
        if (BuiltInFunction.builtInFunctions.containsKey(name)) {
            return BuiltInFunction.builtInFunctions.get(name).call();
        }
        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
