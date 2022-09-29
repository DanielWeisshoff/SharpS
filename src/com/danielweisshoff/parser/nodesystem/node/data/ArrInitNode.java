package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

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

        //generate an id for the variable
        long id = IdRegistry.newID();

        VariableEntry ve = new VariableEntry(name, id, an);
        Interpreter.stm.addVariable(id, ve);

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
