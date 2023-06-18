package parser.nodesystem.node.data.var;

import interpreter.Interpreter;
import parser.nodesystem.DataType;
import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.NodeType;

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
        Interpreter.instance.addVariable(name, Data.New(dataType));

        return new Bool(true);
    }

    @Override
    public void print() {
        super.print();
    }
}