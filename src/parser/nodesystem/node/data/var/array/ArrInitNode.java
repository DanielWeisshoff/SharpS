package parser.nodesystem.node.data.var.array;

import interpreter.Interpreter;
import logger.Logger;
import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.binaryoperations.NumberNode;

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

    @Override
    public void print() {
        super.print();
    }
}
