package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;

/**
 * Initializes a variable
 */
public class DeclareNode extends Node {
    private final String name;
    public final DataType dataType;

    public DeclareNode(String name, DataType dataType) {
        super(null, null, NodeType.DECLARE_NODE);

        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    @Override
    public Data run() {
        //entry in symboltable
        VariableNode vn = new VariableNode(name, dataType);
        vn.data = new Data();

        Interpreter.instance.addVariable(name, vn);

        return new Data();
    }

    //TODO implementation 2.0
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced(name + " : " + dataType, depth + 1);
    }
}