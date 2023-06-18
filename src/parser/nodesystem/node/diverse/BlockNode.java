package parser.nodesystem.node.diverse;

import java.util.ArrayList;

import interpreter.Interpreter;
import parser.nodesystem.DataType;
import parser.nodesystem.data.Data;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

/**
 * Represents a code block.
 */
public class BlockNode extends Node {

    private ArrayList<Node> children;
    private String name;

    public BlockNode(String name) {
        super(null, null, NodeType.BLOCK_NODE);
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

        return Data.New(DataType.VOID);
    }

    @Override
    public void print() {
        Node.depth++;

        for (Node n : children)
            n.print();

        Node.depth--;
    }
}
