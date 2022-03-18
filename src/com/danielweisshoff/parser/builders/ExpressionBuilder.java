package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryAddNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryDivNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryMulNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinarySubNode;
import com.danielweisshoff.parser.nodesystem.node.data.NumberNode;

public class ExpressionBuilder {

	public static Node buildExpression(Parser p) {
		Node left = buildTerm(p);

		Node op = null;
		while (p.curToken.isLineOP()) {

			if (p.curToken.type() == TokenType.ADD)
				op = new BinaryAddNode();
			else if (p.curToken.type() == TokenType.SUB)
				op = new BinarySubNode();
			p.advance();

			Node right = buildTerm(p);

			((BinaryOperationNode) op).left = left;
			((BinaryOperationNode) op).right = right;
			left = op;
		}
		//new PError("Error building term. Symbol '" + p.curToken.getValue() + "' is not a known operator");
		return left;
	}

	private static Node buildTerm(Parser p) {
		Node left = buildFactor(p);

		Node op = null;
		while (p.curToken.isDotOP()) {

			if (p.curToken.type() == TokenType.MUL)
				op = new BinaryMulNode();
			else if (p.curToken.type() == TokenType.DIV)
				op = new BinaryDivNode();
			p.advance();

			Node right = buildFactor(p);

			((BinaryOperationNode) op).left = left;
			((BinaryOperationNode) op).right = right;
			left = op;
		}
		//new PError("Error building term. Symbol '" + p.curToken.getValue() + "' is not a known operator");

		return left;
	}

	private static Node buildFactor(Parser p) {
		int isUnary = 1;

		if (p.curToken.type() == TokenType.SUB) {
			isUnary = -1;
			p.advance();
		}

		if (p.curToken.type() == TokenType.NUMBER) {
			Node n = new NumberNode(Integer.parseInt(p.curToken.getValue()) * isUnary);
			p.advance();
			return n;
		} else if (p.curToken.type() == TokenType.O_ROUND_BRACKET) {
			p.advance();

			Node n = buildExpression(p);

			if (p.curToken.type() != TokenType.C_ROUND_BRACKET)
				new PError("Expression error. Bracket not properly closed");
			p.advance();
			return n;
		} else
			new PError("Expr error, couldnt build factor");
		return null;
	}
}
