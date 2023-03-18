package parser.nodesystem.node.data.var;

import interpreter.Interpreter;
import parser.PError.UnimplementedError;
import parser.nodesystem.Data;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.binaryoperations.NumberNode;

/**Represents a variable and returns the stored data*/
public class VariableNode extends NumberNode {

    public final String name;

    public VariableNode(String name) {
        super(null, null, NodeType.VARIABLE_NODE);
        this.name = name;
    }

    @Override
    public Data run() {
        Data data = Interpreter.instance.findVariable(name);
        if (data == null)
            new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

        return data;
    }

    @Override
    public void print() {
        super.print();
    }
}
