package com.danielweisshoff.parser.nodesystem.node.data.var.shortcuts;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.var.AssignNode;

public class PreDecrementNode extends AssignNode {

    public PreDecrementNode(String name) {
        super(name, NodeType.PRE_DECREMENT_NODE);

    }

    @Override
    public Data run() {

        Data data = Interpreter.instance.findVariable(name);
        if (data == null)
            new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

        double value = data.asDouble();
        data.setValue(value - 1);

        return data;
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
    }
}
