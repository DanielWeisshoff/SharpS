package parser.nodesystem.node.diverse;

import parser.nodesystem.data.Data;
import parser.nodesystem.data.VoidPtr;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.logic.conditions.ConditionNode;

//TODO? elseBlock wird mit null init
public class IfNode extends Node {

    public ConditionNode condition;
    public BlockNode block, elseBlock;

    public IfNode(ConditionNode condition) {
        super(null, null, NodeType.IF_NODE);
        this.condition = condition;
    }

    @Override
    public Data run() {

        boolean conditionMet = ((Numerical) condition.run()).value.byteValue() != 0 ? true : false;
        if (conditionMet)
            block.run();
        else if (elseBlock != null)
            elseBlock.run();

        //TODO empty
        return new VoidPtr();
    }

    @Override
    public void print() {
        super.print();
        block.print();

        if (elseBlock != null)
            elseBlock.print();
    }
}
