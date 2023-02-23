package com.danielweisshoff.parser.nodesystem.node.data.var.array;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class ArrInitNode extends Node {

    private final String name;
    public final DataType dataType;
    public final NumberNode size;

    public ArrInitNode(String name, DataType dataType, NumberNode size) {
        super(null, null, NodeType.ARRAY_NODE);
        this.name = name;
        this.dataType = dataType;
        this.size = size;
    }

    @Override
    public Data run() {
        int fieldsSize = size.run().asInt();

        Data arr = new Data(dataType, fieldsSize);

        for (int i = 0; i < fieldsSize; i++)
            arr.setValue(0, i);

        Interpreter.instance.addVariable(name, arr);

        if (Interpreter.debug)
            Logger.log(name + ", " + dataType + "[]");

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
