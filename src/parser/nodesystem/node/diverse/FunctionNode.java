package parser.nodesystem.node.diverse;

import logger.Channel;
import logger.Logger;
import parser.nodesystem.Data;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

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

    public void add(Node n) {
        block.add(n);
    }

    @Override
    public void print() {
        super.print();
        block.print();
    }
}
