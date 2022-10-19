package com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.AssignNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class PreDecrementNode extends AssignNode {

    public PreDecrementNode(String name) {
        super(name, NodeType.PRE_DECREMENT_NODE);

    }

    @Override
    public Data run() {

        VariableEntry var = Interpreter.instance.findVariable(name);
        if (var == null)
            new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

        double value = var.node.data.asDouble();
        var.node.data.setValue(value - 1);

        return var.node.data;
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
    }
}
