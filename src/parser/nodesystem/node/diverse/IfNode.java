package parser.nodesystem.node.diverse;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.logic.conditions.ConditionNode;

//TODO? elseBlock wird mit null init
public class IfNode extends Node {

    public ConditionNode condition;
    public BlockNode block, elseBlock;

    public IfNode(ConditionNode condition) {
        super(new DataType[] {}, DataType.BOOLEAN, NodeType.IF_NODE);
        this.condition = condition;
    }

    @Override
    public Data run() {

        if (condition.run().asBoolean())
            block.run();
        else if (elseBlock != null)
            elseBlock.run();

        //TODO empty
        return new Data();
    }

    @Override
    public void print() {
        super.print();
        block.print();

        if (elseBlock != null)
            elseBlock.print();
    }
}
