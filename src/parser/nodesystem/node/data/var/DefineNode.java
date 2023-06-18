package parser.nodesystem.node.data.var;

import interpreter.Interpreter;
import parser.nodesystem.data.Data;
import parser.nodesystem.data.VoidPtr;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

// <ID> = <EXPR>
public class DefineNode extends AssignNode {

    public final Node expression;

    public DefineNode(String name, Node expression) {
        super(name, NodeType.DEFINE_NODE);

        this.expression = expression;
    }

    @Override
    public Data run() {

        Data value = expression.run();

        Data data = Interpreter.instance.findVariable(name);
        data.setValue(value);

       return new VoidPtr();
    }

    @Override
    public void print() {
        super.print();
        expression.print();
    }
}
