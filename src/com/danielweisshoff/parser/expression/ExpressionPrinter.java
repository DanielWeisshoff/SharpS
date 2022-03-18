package com.danielweisshoff.parser.expression;

import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryAddNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryDivNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryMulNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinarySubNode;
import com.danielweisshoff.parser.nodesystem.node.data.NumberNode;

//Helps printing out the order of operations for expressions
public class ExpressionPrinter {

	private Node expression;

	public ExpressionPrinter(Node expression) {
		this.expression = expression;
	}

	public void print() {
		print(expression);
	}

	public void print(Node n) {

		if (n instanceof NumberNode) {
			System.out.print(n.execute().asFloat()); //NumberNode
			return;
		}

		System.out.print("(");

		print(((BinaryOperationNode) n).left);

		String op = "?";
		if (n instanceof BinaryAddNode)
			op = "PLUS";
		else if (n instanceof BinarySubNode)
			op = "MINUS";
		else if (n instanceof BinaryMulNode)
			op = "MUL";
		else if (n instanceof BinaryDivNode)
			op = "DIV";
		System.out.print(" " + op + " ");

		print(((BinaryOperationNode) n).right);

		System.out.print(")");
	}
}
