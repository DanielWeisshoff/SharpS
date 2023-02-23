package com.danielweisshoff.parser.nodesystem.node.data.var.array;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class ArrGetFieldNode extends NumberNode {

    String name;
    NumberNode index;

    public ArrGetFieldNode(String name, NumberNode index) {
        super(null, null, NodeType.ARRAY_GET_FIELD_NODE);
        this.name = name;
        this.index = index;
    }

    @Override
    public Data run() {
        Data data = Interpreter.instance.findVariable(name);

        //TODO idk
        double value = data.asDouble(index.run().asInt());
        Data d = new Data(value, DataType.DOUBLE);
        return d;
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
