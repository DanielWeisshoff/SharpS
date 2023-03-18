package parser.nodesystem.node.loops;

import parser.nodesystem.Data;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.diverse.BlockNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;

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

    @Override
    public void print() {
        super.print();
        whileBlock.print();
    }
}
