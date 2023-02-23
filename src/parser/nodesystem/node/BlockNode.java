package parser.nodesystem.node;

import java.util.ArrayList;

import interpreter.Interpreter;
import parser.nodesystem.Data;
import parser.nodesystem.DataType;

/**
 * Represents a code block.
 */
public class BlockNode extends Node {

    private ArrayList<Node> children;
    private String name;

    public BlockNode(String name) {
        super(null, DataType.INT, NodeType.BLOCK_NODE);
        this.name = name;
        children = new ArrayList<>();
    }

    public void add(Node n) {
        children.add(n);
    }

    @Override
    public Data run() {
        Interpreter.instance.newStackFrame();
        for (Node n : children)
            n.run();
        Interpreter.instance.popStackFrame();

        return new Data();
    }

    @Override
    public void print(int depth) {
        for (Node n : children)
            n.print(depth);
    }
}
