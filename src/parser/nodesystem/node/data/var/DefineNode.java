package parser.nodesystem.node.data.var;

import interpreter.Interpreter;
import parser.nodesystem.Data;
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

        double value = expression.run().asDouble();

        Data data = Interpreter.instance.findVariable(name);
        data.setValue(value);

        return new Data();
    }

    @Override
    public void print() {
        super.print();
        expression.print();
    }
}
