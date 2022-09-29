package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.VarInitNode;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;

public class ForNode extends Node {

    public VarInitNode init;
    public ConditionNode condition;
    public AssignNode assignment;
    public BlockNode block;

    public ForNode() {
        super(null, null, NodeType.FOR_NODE);
    }

    @Override
    public Data run() {

        Interpreter.stm.newScope("for-init");
        init.run();

        Interpreter.stm.newScope("for-body");
        while (condition.run().asBoolean()) {
            block.run();
            assignment.run();
        }
        Interpreter.stm.endScope();
        Interpreter.stm.endScope();

        //TODO empty
        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);

        printAdvanced(init, depth + 1);
        printAdvanced(condition, depth + 1);
        printAdvanced(assignment, depth + 1);

        block.print(depth + 1);
    }
}
