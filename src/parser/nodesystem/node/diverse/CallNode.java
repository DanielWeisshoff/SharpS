package parser.nodesystem.node.diverse;

import interpreter.builtin.functions.BuiltInFunction;
import parser.nodesystem.DataType;
import parser.nodesystem.data.Data;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

/**
 * Calls the defined function
 */
public class CallNode extends Node {
    public final String name;

    public CallNode(String name) {
        super(null, null, NodeType.CALL_NODE);
        this.name = name;
    }

    @Override
    public Data run() {
        if (BuiltInFunction.builtInFunctions.containsKey(name)) {
            return BuiltInFunction.builtInFunctions.get(name).call();
        }
        return Data.New(DataType.VOID);
    }

    @Override
    public void print() {
        super.print();
    }
}
