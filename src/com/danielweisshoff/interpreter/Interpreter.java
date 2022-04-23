package com.danielweisshoff.interpreter;

import java.util.Scanner;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.*;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.*;
import com.danielweisshoff.parser.nodesystem.node.data.*;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.*;
import com.danielweisshoff.parser.nodesystem.node.logic.*;
import com.danielweisshoff.parser.nodesystem.node.loops.*;
import com.danielweisshoff.parser.semantic.ConversionChecker;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class Interpreter {

	public boolean debug = false;
	//Variables and Functions
	private SymbolTableManager symbolTable = new SymbolTableManager();
	private ConversionChecker conversionChecker = new ConversionChecker(symbolTable);
	private Node curInstruction;
	//when stepper is enabled, one line will be interpreted at a time on user input
	private boolean stepFinished = false;
	private Scanner stepperScanner = new Scanner(System.in);

	public Interpreter() {
		symbolTable.deleteTableOnScopeEnd = true;
	}

	public Data<?> interpret(Node n) {

		curInstruction = n;
		//this is the return-value of the instruction
		Data<?> data = null;

		switch (n.nodeType) {
		case BLOCK_NODE -> data = interpretBlockNode();
		case CALL_NODE -> data = interpretCallNode();
		case IF_NODE -> data = interpretIfNode();
		//ARITHMETIC
		case BINARY_ADD_NODE -> data = interpretBinaryOperationNode('+');
		case BINARY_SUB_NODE -> data = interpretBinaryOperationNode('-');
		case BINARY_MUL_NODE -> data = interpretBinaryOperationNode('*');
		case BINARY_DIV_NODE -> data = interpretBinaryOperationNode('/');
		case BINARY_MOD_NODE -> data = interpretBinaryOperationNode('%');
		//PRIMITIVES (type safety)
		case BYTE_NODE -> data = interpretPrimitiveNode();
		case SHORT_NODE -> data = interpretPrimitiveNode();
		case INTEGER_NODE -> data = interpretPrimitiveNode();
		case LONG_NODE -> data = interpretPrimitiveNode();
		case FLOAT_NODE -> data = interpretPrimitiveNode();
		case DOUBLE_NODE -> data = interpretPrimitiveNode();
		//VAR ASSIGNMENT
		case EQUAL_ASSIGN_NODE -> data = interpretEqualAssignNode();
		//INCREMENT / DECREMENT
		case PRE_INCREMENT_NODE -> data = interpretPreIncrementNode();
		case POST_INCREMENT_NODE -> data = interpretPostIncrementNode();
		case PRE_DECREMENT_NODE -> data = interpretPreDecrementNode();
		case POST_DECREMENT_NODE -> data = interpretPostDecrementNode();
		//CONDITIONS
		case LESS_NODE -> data = interpretConditionNode("<");
		case LESS_EQUAL_NODE -> data = interpretConditionNode("<=");
		case MORE_NODE -> data = interpretConditionNode(">");
		case MORE_EQUAL_NODE -> data = interpretConditionNode(">=");
		case EQUAL_NODE -> data = interpretConditionNode("==");
		case NOT_EQUAL_NODE -> data = interpretConditionNode("!=");
		case BOOLEAN_AND_NODE -> data = interpretConditionNode("&&");
		case BOOLEAN_OR_NODE -> data = interpretConditionNode("||");
		case BITWISE_AND_NODE -> data = interpretConditionNode("&");
		case BITWISE_OR_NODE -> data = interpretConditionNode("|");
		//VAR STUFF
		case INIT_NODE -> data = interpretInitNode();
		case VARIABLE_NODE -> data = interpretVariableNode();
		//LOOPS
		case WHILE_NODE -> data = interpretWhileNode();
		case DO_WHILE_NODE -> data = interpretDoWhileNode();
		case FOR_NODE -> data = interpretForNode();
		default -> new PError("[INTERPRETER]: visited unknown Node type " + n.getClass().getSimpleName());
		}

		//Debug Mode
		if (debug && stepFinished) {
			System.out.println("*** PRESS ENTER TO CONTINUE ***");
			stepperScanner.nextLine();
			stepFinished = false;
		}

		return data;
	}

	private Data<?> interpretBlockNode() {
		BlockNode bn = (BlockNode) curInstruction;
		symbolTable.newScope("blockscope");

		for (Node n : bn.children)
			interpret(n);

		symbolTable.endScope();
		return new Data<>();
	}

	private Data<?> interpretCallNode() {
		CallNode cn = (CallNode) curInstruction;
		String fName = cn.getName();

		if (BuiltInFunction.builtInFunctions.containsKey(fName)) {
			Logger.log("BuiltInFunktion " + fName + " wird aufgerufen");
			return BuiltInFunction.builtInFunctions.get(fName).call();
		}
		return new Data<>();
	}

	private Data<?> interpretIfNode() {
		IfNode in = (IfNode) curInstruction;

		Data<?> cond = interpret(in.condition);

		if (cond.asInt() == 1)
			interpret(in.condBlock);
		else if (in.elseBlock != null)
			interpret(in.elseBlock);

		return new Data<>();
	}

	private Data<?> interpretBinaryOperationNode(char op) {
		BinaryOperationNode ban = (BinaryOperationNode) curInstruction;

		double left = interpret(ban.left).asDouble();
		double right = interpret(ban.right).asDouble();
		return switch (op) {
		case '+' -> new Data<Double>(left + right, DataType.DOUBLE);
		case '-' -> new Data<Double>(left - right, DataType.DOUBLE);
		case '*' -> new Data<Double>(left * right, DataType.DOUBLE);
		case '/' -> new Data<Double>(left / right, DataType.DOUBLE);
		case '%' -> new Data<Double>(left % right, DataType.DOUBLE);
		default -> new Data<>();
		};
	}

	private Data<?> interpretPrimitiveNode() {
		PrimitiveNode pn = (PrimitiveNode) curInstruction;
		return pn.getData();
	}

	private Data<?> interpretConditionNode(String op) {
		ConditionNode cn = (ConditionNode) curInstruction;

		double left = interpret(cn.left).asDouble();
		double right = interpret(cn.right).asDouble();

		boolean cond;
		switch (op) {
		case "<" -> cond = left < right;
		case "<=" -> cond = left <= right;
		case ">" -> cond = left > right;
		case ">=" -> cond = left >= right;
		case "==" -> cond = left == right;
		case "!=" -> cond = left != right;
		case "&&" -> cond = (left == 1 && right == 1) ? true : false;
		case "||" -> cond = (left == 1 || right == 1) ? true : false;
		case "&" -> cond = (left == 1 & right == 1) ? true : false;
		case "|" -> cond = (left == 1 | right == 1) ? true : false;
		default -> {
			cond = false;
			new PError("Unknown condition '" + op + "'");
		}
		}

		int stmt = cond ? 1 : 0;
		return new Data<>(stmt, DataType.BOOLEAN);
	}

	private Data<?> interpretInitNode() {
		InitNode in = (InitNode) curInstruction;

		String name = in.getName();

		//schauen, ob variable schon existiert
		if (symbolTable.variableExists(name))
			new PError("var '" + name + "' is already declared");

		//checking semantics
		conversionChecker.convert(in.dataType, in.expression);

		Data<?> data = interpret(in.expression);
		//entry in symboltable
		VariableEntry var = new VariableEntry(name, in.dataType, data);
		symbolTable.addVariableToScope(name, var);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretVariableNode() {
		VariableNode vn = (VariableNode) curInstruction;
		String varName = vn.getName();

		VariableEntry var = symbolTable.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		return var.getData();
	}

	private Data<?> interpretWhileNode() {
		WhileNode wn = (WhileNode) curInstruction;

		while (interpret(wn.condition).asDouble() == 1)
			interpret(wn.whileBlock);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretDoWhileNode() {
		DoWhileNode dwn = (DoWhileNode) curInstruction;

		interpret(dwn.whileBlock);
		while (interpret(dwn.condition).asDouble() == 1)
			interpret(dwn.whileBlock);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretForNode() {
		ForNode fn = (ForNode) curInstruction;

		symbolTable.newScope("for-initVar");
		interpret(fn.init);

		double cond = interpret(fn.condition).asDouble();

		while (cond == 1) {
			interpret(fn.block);
			interpret(fn.assignment);
			cond = interpret(fn.condition).asDouble();
		}
		symbolTable.endScope();

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretEqualAssignNode() {
		AssignNode an = (AssignNode) curInstruction;
		String varName = an.getName();

		Data<?> data = interpret(an.expression);
		double value = data.asDouble();

		//try to find and get the variable from the SymbolTable
		VariableEntry ve = symbolTable.findVariableInScope(varName);
		if (ve == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		switch (ve.dataType) {
		case BYTE -> ve.data = new Data<Byte>((byte) value, DataType.BYTE);
		case SHORT -> ve.data = new Data<Short>((short) value, DataType.SHORT);
		case INT -> ve.data = new Data<Integer>((int) value, DataType.INT);
		case LONG -> ve.data = new Data<Long>((long) value, DataType.LONG);
		case FLOAT -> ve.data = new Data<Float>((float) value, DataType.FLOAT);
		case DOUBLE -> ve.data = new Data<Double>((double) value, DataType.DOUBLE);
		default -> new PError("unimplemented action for datatype " + ve.dataType);
		}
		//TODO just for output testing -> implement print()
		System.out.println(ve.data.data + ", " + ve.dataType);

		return new Data<>(1, DataType.INT);
	}

	//TODO remove
	private Data<?> interpretPreIncrementNode() {
		PreIncrementNode in = (PreIncrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = symbolTable.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = var.getData().asDouble();
		var.data = new Data<Double>(value + 1, var.dataType);

		return var.data;
	}

	//TODO remove
	private Data<?> interpretPreDecrementNode() {
		PreDecrementNode in = (PreDecrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = symbolTable.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = var.getData().asDouble();
		var.data = new Data<Double>(value + -1, var.dataType);

		return var.data;
	}

	//TODO remove
	private Data<?> interpretPostIncrementNode() {
		PostIncrementNode in = (PostIncrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = symbolTable.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = var.getData().asDouble();
		var.data = new Data<Double>(value + 1, var.dataType);

		return new Data<Double>(value, DataType.DOUBLE);
	}

	//TODO remove
	private Data<?> interpretPostDecrementNode() {
		PostDecrementNode in = (PostDecrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = symbolTable.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = var.getData().asDouble();
		var.data = new Data<Double>(value + 1, DataType.DOUBLE);

		return new Data<Double>(value, DataType.DOUBLE);
	}

	public SymbolTableManager getSymbolTableManager() {
		return symbolTable;
	}

	//TODO currently not working bcs of hashmap impl.
	// public void printSymbolTable() {
	//		if(debug)
	//	symbolTable.print();
	// }

}