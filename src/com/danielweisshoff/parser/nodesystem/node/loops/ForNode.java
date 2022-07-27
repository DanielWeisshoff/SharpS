package com.danielweisshoff.parser.nodesystem.node.loops;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.VarInitNode;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class ForNode extends Node {

	public VarInitNode init;
	public ConditionNode condition;
	public BlockNode block;
	public AssignNode assignment;

	public ForNode() {
		super(null, null, NodeType.FOR_NODE);
	}

	@Override
	public Data run() {

		init.run();

		while (condition.run().asBoolean()) {
			block.run();
			assignment.run();
		}

		//TODO empty
		return new Data();
	}
}
