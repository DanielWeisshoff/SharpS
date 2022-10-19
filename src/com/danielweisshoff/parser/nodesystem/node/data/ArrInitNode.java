package com.danielweisshoff.parser.nodesystem.node.data;

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

        ArrayNode an = new ArrayNode(name, dataType, size);
        int fieldsSize = size.run().asInt();

        an.fields = new Data[fieldsSize];

        for (int i = 0; i < fieldsSize; i++)
            an.fields[i] = new Data();
        an.data = size.run();

        Interpreter.instance.addVariable(name, an, dataType);

        if (Interpreter.debug) {
            Logger.log(an.data.asInt() + ", " + dataType + "[]");
        }

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
