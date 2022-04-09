package com.danielweisshoff.utils;
// package com.danielweisshoff.parser;

// import com.danielweisshoff.logger.Logger;
// import com.danielweisshoff.parser.nodesystem.node.Node;
// import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryAddNode;
// import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryDivNode;
// import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryMulNode;
// import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
// import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinarySubNode;
// import com.danielweisshoff.parser.nodesystem.node.data.NumberNode;
// import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;

// //Helps printing out the order of operations for expressions
// public class ExpressionPrinter {

// 	private Node expression;

// 	public ExpressionPrinter(Node expression) {
// 		this.expression = expression;
// 	}

// 	public void print() {
// 		print(expression);
// 	}

// 	//TODO schlecht geregelt
// 	public void print(Node n) {

// 		if (n instanceof NumberNode) {
// 			NumberNode nn = (NumberNode) n;
// 			Logger.log("" + nn.getData().asDouble());
// 			return;
// 		} else if (n instanceof VariableNode) {
// 			VariableNode vn = (VariableNode) n;
// 			String varName = vn.getName();
// 			Logger.log(varName);
// 			return;
// 		}

// 		print(((BinaryOperationNode) n).left);

// 		String op = "?";
// 		if (n instanceof BinaryAddNode)
// 			op = "PLUS";
// 		else if (n instanceof BinarySubNode)
// 			op = "MINUS";
// 		else if (n instanceof BinaryMulNode)
// 			op = "MUL";
// 		else if (n instanceof BinaryDivNode)
// 			op = "DIV";

// 		print(((BinaryOperationNode) n).right);

// 		Logger.log("( " + op + " )");
// 	}
// }
