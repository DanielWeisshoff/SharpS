package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.expression.ExpressionPrinter;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.data.InitNode;

public class VariableBuilder {

	public static Node buildVariable(Parser p) {

		//keyword checken
		String keyword = p.curToken.getValue();
		if (!keyword.equals("INT"))
			new PError("Unknown primitive type " + keyword);
		p.advance();

		if (!p.is(TokenType.IDENTIFIER))
			new PError("Fehler beim Initialisieren einer Variable");
		String varName = p.curToken.getValue();

		p.advance();

		Node n;
		if (p.is(TokenType.ASSIGN)) {
			//Variable wird initialisiert

			p.advance();
			Node expr = ExpressionBuilder.buildExpression(p);
			System.out.println(expr.execute().asFloat()); //TODO testing
			new ExpressionPrinter(expr).print();

			Logger.log("Variable " + varName + " initialisiert");
			return new InitNode(varName, expr);
		} else {
			//Variable wird deklariert
			n = new InitNode(varName);
			Logger.log("Variable " + varName + " deklariert");
		}
		p.advance();

		return n;
	}

	// public static AssignNode assignVariable(Parser p) {
	// 	String varName = p.curToken.getValue();
	// 	// if (!Parser.variables.containsKey(varName))
	// 	// 	new PError("Variable existiert nicht");

	// 	p.advance(); //Varname
	// 	p.advance(); //Gleichzeichen
	// 	Node expr = ExpressionBuilder.buildExpression(p);
	// 	Logger.log("Variablenwert ver�ndert");
	// 	return new AssignNode(varName, expr);
	// }
}
