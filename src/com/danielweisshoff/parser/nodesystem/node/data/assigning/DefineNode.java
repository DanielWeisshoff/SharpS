package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class DefineNode extends AssignNode {

    public final Node expression;

    public DefineNode(String name, Node expression) {
        super(name, NodeType.DEFINE_NODE);

        this.expression = expression;
    }

    @Override
    public Data run() {

        double value = expression.run().asDouble();

        VariableEntry entry = Interpreter.instance.findVariable(name);
        entry.node.data.setValue(value);

        if (Interpreter.debug)
            Logger.log(value + ", " + ((VariableNode) entry.node).getDataType());

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        expression.print(depth + 1);
    }
}
