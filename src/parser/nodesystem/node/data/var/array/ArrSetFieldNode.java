package parser.nodesystem.node.data.var.array;

import interpreter.Interpreter;
import logger.Logger;
import parser.nodesystem.data.Array;
import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.primitives.NumberNode;

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

        Array an = (Array) Interpreter.instance.findVariable(name);

        an.setData(value.run(), index.run());

        if (Interpreter.debug)
            Logger.log(an.getBaseData(index.run()) + ", " + name + "[" + index + "]");

        return new Bool(true);
    }

    @Override
    public void print() {
        super.print();
    }
}
