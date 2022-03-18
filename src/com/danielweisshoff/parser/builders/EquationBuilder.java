package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.logic.*;

public class EquationBuilder {

	public static Node buildEquation(Parser p, Node leftExpression) {
		Token compareType = p.curToken;
		p.advance();
		Node rightExpression = ExpressionBuilder.buildExpression(p);
		Logger.log("Gleichung erstellt");

		return switch (compareType.getValue()) {
			case "<" -> new LessNode(leftExpression, rightExpression);
			case "<=" -> new LessEqualNode(leftExpression, rightExpression);
			case ">" -> new MoreNode(leftExpression, rightExpression);
			case ">=" -> new MoreEqualNode(leftExpression, rightExpression);
			case "==" -> new EqualNode(leftExpression, rightExpression);
			case "!=" -> new NotEqualNode(leftExpression, rightExpression);
			default -> null;
		};
	}
}
