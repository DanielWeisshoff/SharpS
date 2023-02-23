package parser.nodesystem.node.data.var;

import interpreter.Interpreter;
import parser.PError.UnimplementedError;
import parser.nodesystem.Data;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.binaryoperations.NumberNode;

/* TODO
 *   - Sollte eventuell ID anstatt Name speichern
 */

/**
 * Holds the name of a variable and returns the stored data
 */
public class VariableNode extends NumberNode {

    private final String name;

    public VariableNode(String name) {
        super(null, null, NodeType.VARIABLE_NODE);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Data run() {

        Data data = Interpreter.instance.findVariable(name);
        if (data == null)
            new UnimplementedError("Interpreter Error: var '" + name + "' not declared");

        return data;
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
    }
}
