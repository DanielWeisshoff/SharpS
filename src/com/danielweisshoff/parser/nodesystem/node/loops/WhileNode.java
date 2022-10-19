package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

public class WhileNode extends Node {

    public ConditionNode condition;
    public BlockNode whileBlock;

    public WhileNode(ConditionNode condition) {
        super(null, null, NodeType.WHILE_NODE);
        this.condition = condition;
    }

    @Override
    public Data run() {
        while (condition.run().asBoolean())
            whileBlock.run();
        //TODO empty

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        printAdvanced(condition, depth);

        whileBlock.print(depth + 1);
    }
}
