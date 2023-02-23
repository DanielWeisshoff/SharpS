package parser.nodesystem.node.data.var.array;

import interpreter.Interpreter;
import logger.Logger;
import parser.nodesystem.Data;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.binaryoperations.NumberNode;

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
        Data an = Interpreter.instance.findVariable(name);

        int i = index.run().asInt();
        an.setValue(value.run().asDouble(), i);

        if (Interpreter.debug)
            Logger.log(an.asDouble(i) + ", " + name + "[" + i + "]");

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
