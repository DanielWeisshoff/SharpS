package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.IfNode;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;

public class IfBuilder {

	public static IfNode buildIf(Parser p) {
		p.assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");
		ConditionNode condition = ConditionBuilder.buildCondition(p);
		p.assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
		p.assume(TokenType.COLON, "Block missing");

		IfNode in = new IfNode();
		in.condition = condition;

		Logger.log("found condition");
		return in;
	}

	public static BlockNode buildElse(Parser p) {

		p.assume(TokenType.COLON, "unknown syntax for else statement");
		p.advance();

		Logger.log("found else");
		return new BlockNode();
	}

	// public static IfNode buildElif(Parser p) {
	// 	BlockNode bn = new BlockNode();

	// 	IfNode in = buildIf(p);
	// 	bn.add(in);

	// 	Logger.log("found else if");
	// 	return bn;
	// }
}
