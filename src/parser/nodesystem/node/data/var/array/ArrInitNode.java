package parser.nodesystem.node.data.var.array;

import interpreter.Interpreter;
import logger.Logger;
import parser.nodesystem.DataType;
import parser.nodesystem.data.Array;
import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.data.numerical.integer.I32;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.primitives.integer.IntegerNode;

public class ArrInitNode extends Node {

    private final String name;
    public final DataType dataType;
    public final IntegerNode size;

    public ArrInitNode(String name, DataType dataType, IntegerNode size) {
        super(null, null, NodeType.ARRAY_NODE);
        this.name = name;
        this.dataType = dataType;
        this.size = size;
    }

    @Override
    public Data run() {
        int fieldsSize = (int) ((I32) (size.run())).value;

        Array arr = new Array(dataType, fieldsSize);

        Interpreter.instance.addVariable(name, arr);

        if (Interpreter.debug)
            Logger.log(name + ", " + dataType + "[]");

        return new Bool(true);
    }

    @Override
    public void print() {
        super.print();
    }
}
