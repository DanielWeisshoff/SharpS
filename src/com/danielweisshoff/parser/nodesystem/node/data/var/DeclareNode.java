package com.danielweisshoff.parser.nodesystem.node.data.var;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;

// <PRIMITIVE> <ID>
public class DeclareNode extends AssignNode {
    public final String name;
    public final DataType dataType;

    public DeclareNode(String name, DataType dataType) {
        super(name, NodeType.DECLARE_NODE);

        this.name = name;
        this.dataType = dataType;
    }

    @Override
    public Data run() {
        //TODO
        Interpreter.instance.addVariable(name, new Data(dataType));

        return new Data();
    }

    //TODO implementation 2.0
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced(name + " : " + dataType, depth + 1);
    }
}