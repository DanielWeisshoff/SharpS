package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

public class DoWhileNode extends Node {

    public ConditionNode condition;
    public BlockNode whileBlock;

    public DoWhileNode(ConditionNode condition) {
        super(null, null, NodeType.DO_WHILE_NODE);
        this.condition = condition;
    }

    @Override
    public Data run() {
        do {
            whileBlock.run();
        } while (condition.run().asBoolean());

        //TODO empty
        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }
}
