package com.danielweisshoff.interpreter;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.CallNode;
import com.danielweisshoff.parser.nodesystem.node.IfNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryAddNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryDivNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryMulNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinarySubNode;
import com.danielweisshoff.parser.nodesystem.node.data.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.InitNode;
import com.danielweisshoff.parser.nodesystem.node.data.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.nodesystem.node.logic.ConditionNode;
import com.danielweisshoff.parser.nodesystem.node.logic.EqualNode;
import com.danielweisshoff.parser.nodesystem.node.logic.LessEqualNode;
import com.danielweisshoff.parser.nodesystem.node.logic.LessNode;
import com.danielweisshoff.parser.nodesystem.node.logic.MoreEqualNode;
import com.danielweisshoff.parser.nodesystem.node.logic.MoreNode;
import com.danielweisshoff.parser.nodesystem.node.logic.NotEqualNode;
import com.danielweisshoff.parser.symboltable.Entry;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.Type;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/*
* Data<>(1, DataType.INT); ist vorerst standard für "erfolgreiche Operation"
*
*/
public class Interpreter {

	//Variables and Functions
	SymbolTableManager symbolTableManager = new SymbolTableManager();

	private Node curInstruction;

	public Data<?> interpret(Node n) {
		curInstruction = n;
		Data<?> data = null;
		//n.execute();

		if (n instanceof BlockNode)
			data = interpretBlockNode();
		else if (n instanceof CallNode)
			data = interpretCallNode();
		else if (n instanceof IfNode)
			data = interpretIfNode();
		//ARITHMETIC
		else if (n instanceof BinaryAddNode)
			data = interpretBinaryOperationNode('+');
		else if (n instanceof BinarySubNode)
			data = interpretBinaryOperationNode('-');
		else if (n instanceof BinaryMulNode)
			data = interpretBinaryOperationNode('*');
		else if (n instanceof BinaryDivNode)
			data = interpretBinaryOperationNode('/');
		else if (n instanceof NumberNode)
			data = interpretNumberNode();
		//CONDITIONS
		else if (n instanceof LessNode)
			data = interpretConditionNode("<");
		else if (n instanceof LessEqualNode)
			data = interpretConditionNode("<=");
		else if (n instanceof MoreNode)
			data = interpretConditionNode(">");
		else if (n instanceof MoreEqualNode)
			data = interpretConditionNode(">=");
		else if (n instanceof EqualNode)
			data = interpretConditionNode("==");
		else if (n instanceof NotEqualNode)
			data = interpretConditionNode("!=");
		//VAR STUFF
		else if (n instanceof InitNode)
			data = interpretInitNode();
		else if (n instanceof AssignNode)
			data = interpretAssignNode();
		else if (n instanceof VariableNode)
			data = interpretVariableNode();
		else
			new PError("[INTERPRETER]: visited unknown Node type " + n.getClass().getSimpleName());

		return data;
	}

	private Data<?> interpretBlockNode() {
		BlockNode bn = (BlockNode) curInstruction;
		symbolTableManager.newScope("blockscope");
		for (Node n : bn.children)
			interpret(n);
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
		default -> new Data<>();
		};
	}

	private Data<?> interpretNumberNode() {
		NumberNode nn = (NumberNode) curInstruction;
		return nn.getData();
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
		default -> cond = false;
		}

		if (cond)
			return new Data<>(1, DataType.BOOLEAN);
		else
			return new Data<>(0, DataType.BOOLEAN);
	}

	private Data<?> interpretInitNode() {
		InitNode in = (InitNode) curInstruction;

		String name = in.getName();

		Entry e = symbolTableManager.findInCurrentScope(name, Type.VARIABLE);
		if (e != null)
			new PError("var '" + name + "' is already declared");

		Data<?> data = interpret(in.expression);
		String value = "" + data.asDouble(); //TODO naja, weiss nich

		//entry in symboltable
		VariableEntry var = new VariableEntry(name, DataType.DOUBLE, value);
		symbolTableManager.addToScope(var);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretAssignNode() {
		AssignNode an = (AssignNode) curInstruction;
		String varName = an.getName();

		Data<?> data = interpret(an.expression);
		String value = "" + data.asDouble(); //TODO naja, weiss nich

		//try to find and get the variable from the SymbolTable
		VariableEntry ve = (VariableEntry) symbolTableManager.findInCurrentScope(varName, Type.VARIABLE);
		if (ve == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		ve.value = value;

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretVariableNode() {
		VariableNode vn = (VariableNode) curInstruction;
		String varName = vn.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findInCurrentScope(varName, Type.VARIABLE);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = Double.parseDouble(var.getValue());
		return new Data<Double>(value, DataType.DOUBLE);
	}

	public SymbolTableManager getSymbolTableManager() {
		return symbolTableManager;
	}

}