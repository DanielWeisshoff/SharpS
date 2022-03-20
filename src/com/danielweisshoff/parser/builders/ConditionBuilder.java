package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.logic.*;

/*

TODO:

Condition		: Condition AND|OR	CONDITION

						3 > 2 && 2*3 < 200
*/
public class ConditionBuilder {

	public static ConditionNode buildCondition(Parser p) {
		Node leftExpr = ExpressionBuilder.buildExpression(p);
		Token compareType = p.curToken;
		p.advance();
		Node rightExpr = ExpressionBuilder.buildExpression(p);

		Logger.log("Gleichung erstellt");

		return switch (compareType.getValue()) {
		case "<" -> new LessNode(leftExpr, rightExpr);
		case "<=" -> new LessEqualNode(leftExpr, rightExpr);
		case ">" -> new MoreNode(leftExpr, rightExpr);
		case ">=" -> new MoreEqualNode(leftExpr, rightExpr);
		case "==" -> new EqualNode(leftExpr, rightExpr);
		case "!=" -> new NotEqualNode(leftExpr, rightExpr);
		default -> null;
		};
	}
}
