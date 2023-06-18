package parser.nodesystem.node.data.var.array;

import interpreter.Interpreter;
import parser.nodesystem.DataType;
import parser.nodesystem.data.Array;
import parser.nodesystem.data.Data;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.primitives.NumberNode;

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
        Data data = Interpreter.instance.findVariable(name);

        if (data.type != DataType.ARRAY)
            throw new Error("Expected Array type but got " + data.type);

        Array array = (Array) data;
        return array.getBaseData(index.run());
    }

    @Override
    public void print() {
        super.print();
    }
}
