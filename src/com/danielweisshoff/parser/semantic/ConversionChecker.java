package com.danielweisshoff.parser.semantic;

import java.util.HashMap;

import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.data.PrimitiveNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.*;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.VariableEntry;

//Checks if the given expression can be casted to the primitive type
//In the process replaces:
//		-IntegerNumberNode
// 		-FloatingPointNumberNode
//with the fitting primitive-type Nodes

//TODO wEiRd NaMe
public class ConversionChecker {

	//lower values can be casted into higher or same precedence
	public static HashMap<DataType, Integer> precedences = new HashMap<>() {
		{
			put(DataType.BYTE, 0);
			put(DataType.SHORT, 1);
			put(DataType.INT, 2);
			put(DataType.LONG, 3);
			put(DataType.FLOAT, 4);
			put(DataType.DOUBLE, 5);
		}
	};

	private static String lastPrimitiveType;
	private static int precedence;
	private static SymbolTableManager symbolTableManager;

	public static void setSymbolTableManager(SymbolTableManager symbolTableManager) {
		ConversionChecker.symbolTableManager = symbolTableManager;
	}

	public static boolean convert(DataType dataType, Node expr) {
		precedence = precedences.get(dataType);

		if (!check(expr))
			new PError("CONVERSION ERROR: can't convert from " + lastPrimitiveType + " to " + dataType);

		return true;
	}

	private static boolean check(Node expr) {
		if (expr instanceof BinaryOperationNode)
			return traverseOperation(expr);
		else if (expr instanceof PrimitiveNode)
			return checkPrimitive(expr);
		else if (expr instanceof VariableNode)
			return checkVariable(expr);
		else {
			new PError("conv: Unknown Node " + expr.getClass().getSimpleName());
			return false;
		}
	}

	//
	// TYPE CHECKING
	//

	private static boolean traverseOperation(Node expr) {
		boolean l = check(((BinaryOperationNode) expr).left);
		boolean r = check(((BinaryOperationNode) expr).right);
		return l && r;
	}

	private static boolean checkPrimitive(Node expr) {
		if (expr instanceof ByteNode)
			return precedence >= precedences.get(DataType.BYTE);
		else if (expr instanceof ShortNode)
			return precedence >= precedences.get(DataType.SHORT);
		else if (expr instanceof IntegerNode)
			return precedence >= precedences.get(DataType.INT);
		else if (expr instanceof LongNode)
			return precedence >= precedences.get(DataType.LONG);
		//floating point
		else if (expr instanceof FloatNode)
			return precedence >= precedences.get(DataType.FLOAT);
		else if (expr instanceof DoubleNode)
			return precedence >= precedences.get(DataType.DOUBLE);
		else {
			new PError("conversion error");
			return false;
		}
	}

	private static boolean checkVariable(Node expr) {
		VariableNode vn = (VariableNode) expr;
		VariableEntry ve = symbolTableManager.findVariableInScope(vn.getName());

		lastPrimitiveType = "" + ve.dataType;
		return precedence >= precedences.get(ve.dataType);
	}

	public static boolean isByte(String value) {
		try {
			Byte.parseByte(value);
			lastPrimitiveType = "BYTE";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isShort(String value) {
		try {
			Short.parseShort(value);
			lastPrimitiveType = "SHORT";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isInt(String value) {
		try {
			Integer.parseInt("" + value);
			lastPrimitiveType = "INT";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			lastPrimitiveType = "LONG";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isFloat(String value) {
		try {
			Float.parseFloat(value);
			lastPrimitiveType = "FLOAT";
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDouble(String value) {
		try {
			Double.parseDouble("" + value);
			lastPrimitiveType = "DOUBLE";
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
