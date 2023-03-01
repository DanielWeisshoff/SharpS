package parser.nodesystem.node;

import logger.Logger;
import logger.Logger.Channel;
import parser.nodesystem.Data;

public class FunctionNode extends Node {

    public String name;
    public BlockNode block;

    public FunctionNode(String name) {
        super(null, null, NodeType.FUNCTION_NODE);
        this.name = name;
    }

    @Override
    public Data run() {
        Logger.log("calling function " + name + "()", Channel.INTERPRETER);
        return block.run();
    }

    @Override
    public void print(int depth) {
        block.print(depth);
    }

    public void add(Node n) {
        block.add(n);
    }
}
