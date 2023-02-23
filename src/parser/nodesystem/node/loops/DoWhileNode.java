package parser.nodesystem.node.loops;

import parser.nodesystem.Data;
import parser.nodesystem.node.BlockNode;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.logic.conditions.ConditionNode;

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
