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
    public AssignNode increment;
    public BlockNode block;

    public ForNode() {
        super(null, null, NodeType.FOR_NODE);
    }

    @Override
    public Data run() {
        //new Stackframe for the index variable
        Interpreter.instance.newStackFrame();
        init.run();

        System.out.println("one loop cycle");
        while (condition.run().asBoolean()) {
            block.run();
            increment.run();
        }
        //closing Stackframe of index variable
        Interpreter.instance.popStackFrame();

        //TODO empty
        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);

        printAdvanced(init, depth + 1);
        printAdvanced(condition, depth + 1);
        printAdvanced(increment, depth + 1);

        block.print(depth + 1);
    }
}
