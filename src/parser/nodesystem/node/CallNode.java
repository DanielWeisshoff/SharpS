package parser.nodesystem.node;

import interpreter.builtin.functions.BuiltInFunction;
import parser.nodesystem.Data;

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
        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
    }
}
