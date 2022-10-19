package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;

public class VarInitNode extends AssignNode {

    private final String name;
    public final DataType dataType;
    public final NumberNode expression;
    public boolean isPointer = false;

    public VarInitNode(String name, DataType dataType, NumberNode expression) {
        super(name, NodeType.INIT_NODE);

        this.name = name;
        this.dataType = dataType;
        this.expression = expression;
    }

    @Override
    public Data run() {
        VariableNode vn = new VariableNode(name, dataType);
        vn.data = expression.run();

        Interpreter.instance.addVariable(name, vn);

        if (Interpreter.debug) {
            Data data = expression.run();
            Logger.log(data.data + ", " + dataType);
        }

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);

        printAdvanced("name: " + name, depth + 1);
        printAdvanced(expression, depth + 1);
    }
}
