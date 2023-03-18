package parser.nodesystem.node.data.var;

import interpreter.Interpreter;
import parser.nodesystem.Data;
import parser.nodesystem.DataType;
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
        //TODO
        Interpreter.instance.addVariable(name, new Data(dataType));

        return new Data();
    }

    @Override
    public void print() {
        super.print();
    }
}