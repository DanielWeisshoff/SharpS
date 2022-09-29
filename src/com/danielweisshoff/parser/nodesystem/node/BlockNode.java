package com.danielweisshoff.parser.nodesystem.node;

import java.util.ArrayList;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/**
 * Represents a code block.
 */
public class BlockNode extends Node {

    public ArrayList<Node> children;
    public int depth;
    private String name;

    public BlockNode(int depth, String name) {
        super(null, DataType.INT, NodeType.BLOCK_NODE);
        this.name = name;
        this.depth = depth;
        children = new ArrayList<>();
    }

    public void add(Node n) {
        children.add(n);
    }

    @Override
    public Data run() {
        Interpreter.stm.newScope("block");
        for (Node n : children)
            n.run();
        Interpreter.stm.endScope();

        //TODO empty
        return new Data();
    }

    @Override
    public void print(int depth) {

        System.out.println(offset(depth) + nodeType);

        printAdvanced("name: " + name, depth + 1);

        for (Node n : children)
            n.print(depth + 1);
    }
}