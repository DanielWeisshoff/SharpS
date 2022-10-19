package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/*
Saves the name of a vatiable and searches for its id at runtime
*/
public class PointerNode extends NumberNode {

    public String adress;

    public final String name;
    public final DataType dataType;

    public PointerNode(String name, String adress, DataType dataType) {
        super(null, null, NodeType.POINTER_NODE);
        this.name = name;
        this.adress = adress;
        this.dataType = dataType;
    }

    @Override
    public Data run() {

        VariableEntry var = Interpreter.instance.findVariable(adress);
        //TODO in the end this will point to some anyway
        if (var == null)
            new UnimplementedError("Interpreter Error: adress '" + adress + "' empty");

        return data;
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
