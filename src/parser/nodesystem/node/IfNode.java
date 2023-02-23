package parser.nodesystem.node;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;
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

    //TODO implementation 2.0
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);

        printAdvanced("cond: ", depth);

        block.print(depth + 1);

        if (elseBlock != null)
            elseBlock.print(depth + 1);
    }
}
