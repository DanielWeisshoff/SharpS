package parser.nodesystem.node.loops;

import interpreter.Interpreter;
import parser.nodesystem.data.Data;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.variable.VarInitNode;
import parser.nodesystem.node.diverse.BlockNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;

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
        while (condition.run().asBoolean()) {
            block.run();
            increment.run();
        }
        //closing Stackframe of index variable
        Interpreter.instance.popStackFrame();

        //TODO empty
        return new Data();
    }

    @Override
    public void print() {
        super.print();
        block.print();
    }
}
