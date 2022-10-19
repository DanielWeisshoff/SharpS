package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

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
        VariableEntry ve = Interpreter.instance.findVariable(name);

        return ((ArrayNode) ve.node).getField(index.run().asInt());
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
