package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;

public class ArrSetFieldNode extends Node {

    private final String name;
    private final NumberNode index;
    private final NumberNode value;

    public ArrSetFieldNode(String name, NumberNode index, NumberNode value) {
        super(null, null, NodeType.ARRAY_SET_FIELD_NODE);

        this.name = name;
        this.index = index;
        this.value = value;
    }

    @Override
    public Data run() {
        ArrayNode an = (ArrayNode) Interpreter.stm.findVariable(name).node;

        int i = index.run().asInt();
        an.fields[i] = value.run();

        if (Interpreter.debug)
            Logger.log(an.fields[i].asDouble() + ", " + name + "[" + i + "]");

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
