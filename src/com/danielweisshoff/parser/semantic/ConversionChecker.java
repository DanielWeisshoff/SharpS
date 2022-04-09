package com.danielweisshoff.parser.semantic;

import java.util.HashMap;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.data.numbers.FloatingPointNumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.numbers.IntegerNumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.*;

//Checks if the given expression can be casted to the primitive type
//In the process replaces:
//		-IntegerNumberNode
// 		-FloatingPointNumberNode
//with the fitting primitive-type Nodes

//TODO wEiRd NaMe
public class ConversionChecker {

	//lower values can be casted into higher or same precedence
	public static HashMap<TokenType, Integer> precedences = new HashMap<>() {
		{
			put(TokenType.KW_BYTE, 0);
			put(TokenType.KW_SHORT, 1);
			put(TokenType.KW_INT, 2);
			put(TokenType.KW_LONG, 3);
			put(TokenType.KW_FLOAT, 4);
			put(TokenType.KW_DOUBLE, 5);
		}
	};
	public static HashMap<Integer, TokenType> primitives = new HashMap<>() {
		{
			put(0, TokenType.KW_BYTE);
			put(1, TokenType.KW_SHORT);
			put(2, TokenType.KW_INT);
			put(3, TokenType.KW_LONG);
			put(4, TokenType.KW_FLOAT);
			put(5, TokenType.KW_DOUBLE);
		}
	};

	private static String lastPrimitiveType;
	private static int precedence;

	public static boolean convert(TokenType primitive, Node expr) {
		precedence = precedences.get(primitive);

		if (!check(expr))
			new PError("CONVERSION ERROR: can't convert from " + lastPrimitiveType + " to " + primitive);

		return true;
	}

	private static boolean check(Node expr) {
		if (expr instanceof BinaryOperationNode)
			return traverseOperation(expr);
		else if (expr instanceof IntegerNumberNode)
			return checkInteger(expr);
		else if (expr instanceof FloatingPointNumberNode)
			return checkFloatingPoint(expr);
		else
			return false;
	}

	//
	// TYPE CHECKING
	//

	private static boolean traverseOperation(Node expr) {
		boolean l = check(((BinaryOperationNode) expr).left);
		boolean r = check(((BinaryOperationNode) expr).right);
		return l && r;
	}

	private static boolean checkInteger(Node expr) {
		IntegerNumberNode inn = (IntegerNumberNode) expr;
		long value = inn.value;
		boolean canConvert = true;

		if (isByte("" + value)) {
			expr = new ByteNode((byte) value);
			canConvert = precedence >= precedences.get(TokenType.KW_BYTE);
		} else if (isShort("" + value)) {
			expr = new ShortNode((short) value);
			canConvert = precedence >= precedences.get(TokenType.KW_SHORT);
		} else if (isInteger("" + value)) {
			expr = new IntegerNode((int) value);
			canConvert = precedence >= precedences.get(TokenType.KW_INT);
		} else if (isLong("" + value)) {
			expr = new LongNode(value);
			canConvert = precedence >= precedences.get(TokenType.KW_LONG);
		} else
			new PError("Unimplemented Integer primitive type");

		return canConvert;
	}

	private static boolean checkFloatingPoint(Node expr) {
		FloatingPointNumberNode fpnn = (FloatingPointNumberNode) expr;
		double value = fpnn.value;
		boolean canConvert = true;

		if (isFloat("" + value)) {
			expr = new FloatNode((float) value);
			canConvert = precedence >= precedences.get(TokenType.KW_FLOAT);
		} else if (isDouble("" + value)) {
			expr = new DoubleNode((double) value);
			canConvert = precedence >= precedences.get(TokenType.KW_DOUBLE);
		} else
			new PError("Unimplemented Floating Point primitive type");

		return canConvert;
	}

	private static boolean isByte(String value) {
		try {
			Byte.parseByte(value);
			lastPrimitiveType = "byte";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isShort(String value) {
		try {
			Short.parseShort(value);
			lastPrimitiveType = "short";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isInteger(String value) {
		try {
			Integer.parseInt("" + value);
			lastPrimitiveType = "int";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			lastPrimitiveType = "long";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isFloat(String value) {
		try {
			Float.parseFloat(value);
			lastPrimitiveType = "float";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isDouble(String value) {
		try {
			Double.parseDouble("" + value);
			lastPrimitiveType = "double";
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
