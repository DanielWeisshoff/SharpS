package com.danielweisshoff.interpreter;

import java.util.HashMap;

import com.danielweisshoff.parser.PError.*;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.data.PointerNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PostDecrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PostIncrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PreDecrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PreIncrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.*;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.VariableEntry;

//TODO nachdem eine PointerNode gefunden wurde, darf nichts mehr kommen
//TODO Errors entfernen, da es Parsererror Klasse ist
//casting primitives at runtime
public class TypeConversion {

	//lower values can be casted into higher or same precedence
	private static HashMap<DataType, Integer> precedences = new HashMap<>() {
		{
			put(DataType.POINTER, -1);
			put(DataType.BYTE, 0);
			put(DataType.SHORT, 1);
			put(DataType.INT, 2);
			put(DataType.LONG, 3);
			put(DataType.FLOAT, 4);
			put(DataType.DOUBLE, 5);
		}
	};

	private int precedence;
	private DataType lastType;
	private SymbolTableManager symbolTableManager;
	private DataType dataType;

	public TypeConversion(SymbolTableManager symbolTableManager) {
		this.symbolTableManager = symbolTableManager;
	}

	public boolean convert(DataType dataType, Node expr) {
		this.dataType = dataType;

		precedence = precedences.get(dataType);

		if (!check(expr))
			new UnimplementedError("CONVERSION ERROR: can't convert from " + lastType + " to " + dataType);

		return true;
	}

	private boolean check(Node expr) {
		if (expr instanceof BinaryOperationNode)
			return traverseOperation(expr);
		else if (expr instanceof PointerNode)
			return checkPointer(expr);
		else if (expr instanceof PrimitiveNode)
			return checkPrimitive(expr);
		else if (expr instanceof VariableNode)
			return checkVariable(expr);
		else if (expr instanceof PreIncrementNode || expr instanceof PostIncrementNode
				|| expr instanceof PreDecrementNode || expr instanceof PostDecrementNode)
			return checkCrement(expr);
		else {
			new UnimplementedError("conv: Unknown Node " + expr.getClass().getSimpleName());
			return false;
		}
	}

	//
	// TYPE CHECKING
	//

	private boolean traverseOperation(Node expr) {
		boolean l = check(((BinaryOperationNode) expr).left);
		boolean r = check(((BinaryOperationNode) expr).right);
		return l && r;
	}

	private boolean checkPointer(Node expr) {
		PointerNode pn = (PointerNode) expr;

		VariableEntry ve = symbolTableManager.findVariable(pn.getName());
		if (ve == null)
			new UnimplementedError("conv error: var '" + pn.getName() + "' not declared");

		if (ve.dataType == dataType)
			return true;
		else {
			new UnimplementedError("CONVERSION ERROR: can't point from " + lastType + " to " + dataType);
			return false;
		}
	}

	private boolean checkPrimitive(Node expr) {
		lastType = ((PrimitiveNode) expr).getData().dataType;

		//integer 
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
			new UnimplementedError("conversion error");
			return false;
		}
	}

	private boolean checkVariable(Node expr) {
		VariableNode vn = (VariableNode) expr;
		VariableEntry ve = symbolTableManager.findVariable(vn.getName());
		if (ve == null)
			new UnimplementedError("conv error: var '" + vn.getName() + "' not declared");

		lastType = ve.dataType;
		return precedence >= precedences.get(ve.dataType);
	}

	private boolean checkCrement(Node expr) {
		AssignNode an = (AssignNode) expr;

		return precedence >= precedences.get(an.variable.getDataType());
	}
}
