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
import com.danielweisshoff.parser.nodesystem.node.data.assigning.*;
import com.danielweisshoff.parser.nodesystem.node.data.shortcuts.DecrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.shortcuts.IncrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.shortcuts.LateDecrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.shortcuts.LateIncrementNode;
import com.danielweisshoff.parser.nodesystem.node.logic.*;
import com.danielweisshoff.parser.nodesystem.node.loops.*;
import com.danielweisshoff.parser.symboltable.Entry;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/*
* Data<>(1, DataType.INT); ist vorerst standard für "erfolgreiche Operation"
TODO: StepperMode -> Debug mode ?!?
*
* Dieser Interpreter hat keine Runtime-Optimierung, deswegen sind selbst die trivialsten
* Unterschiede zwischen Befehlen in eigene Methoden verpackt, um ein wenig flotter unterwegs zu sein
*/
public class Interpreter {

	//Variables and Functions
	SymbolTableManager symbolTableManager = new SymbolTableManager();

	private Node curInstruction;

	//when stepper is enabled, one line will be interpreted at a time on user input
	public boolean stepper = false;
	private boolean stepFinished = false;

	public Interpreter() {
		symbolTableManager.deleteTableOnScopeEnd = true;
	}

	public Data<?> interpret(Node n) {
		curInstruction = n;
		Data<?> data = null;

		if (n instanceof BlockNode)
			data = interpretBlockNode();
		else if (n instanceof CallNode) {
			data = interpretCallNode();
			stepFinished = true;
		} else if (n instanceof IfNode)
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
		else if (n instanceof BinaryModNode)
			data = interpretBinaryOperationNode('%');
		else if (n instanceof NumberNode)
			data = interpretNumberNode();
		//VAR ASSIGNMENT
		else if (n instanceof EqualAssignNode) {
			data = interpretEqualAssignNode();
			stepFinished = true;
		} else if (n instanceof AddAssignNode)
			data = interpretAddAssignNode();
		else if (n instanceof SubAssignNode)
			data = interpretSubAssignNode();
		else if (n instanceof MulAssignNode)
			data = interpretMulAssignNode();
		else if (n instanceof DivAssignNode)
			data = interpretDivAssignNode();
		else if (n instanceof ModAssignNode)
			data = interpretModAssignNode();
		else if (n instanceof IncrementNode)
			data = interpretIncrementNode();
		else if (n instanceof LateIncrementNode)
			data = interpretLateIncrementNode();
		else if (n instanceof DecrementNode)
			data = interpretDecrementNode();
		else if (n instanceof LateDecrementNode)
			data = interpretLateDecrementNode();
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
		else if (n instanceof BooleanAndNode)
			data = interpretConditionNode("&&");
		else if (n instanceof BooleanOrNode)
			data = interpretConditionNode("||");
		else if (n instanceof BitwiseAndNode)
			data = interpretConditionNode("&");
		else if (n instanceof BitWiseOrNode)
			data = interpretConditionNode("|");
		//VAR STUFF
		else if (n instanceof InitNode) {
			data = interpretInitNode();
			stepFinished = true;
		} else if (n instanceof VariableNode)
			data = interpretVariableNode();
		//LOOPS
		else if (n instanceof WhileNode)
			data = interpretWhileNode();
		else if (n instanceof DoWhileNode)
			data = interpretDoWhileNode();
		else if (n instanceof ForNode)
			data = interpretForNode();
		else
			new PError("[INTERPRETER]: visited unknown Node type " + n.getClass().getSimpleName());

		//Stepper Mode
		if (stepper && stepFinished) {
			System.out.println("*** PRESS ENTER TO CONTINUE ***");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			stepFinished = false;
		}

		return data;
	}

	private Data<?> interpretBlockNode() {
		BlockNode bn = (BlockNode) curInstruction;
		symbolTableManager.newScope("blockscope");

		for (Node n : bn.children)
			interpret(n);

		symbolTableManager.endScope();
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
		case "&&" -> cond = (left == 1 && right == 1) ? true : false;
		case "||" -> cond = (left == 1 || right == 1) ? true : false;
		case "&" -> cond = (left == 1 & right == 1) ? true : false;
		case "|" -> cond = (left == 1 | right == 1) ? true : false;
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

		Entry e = symbolTableManager.findVariableInScope(name);
		if (e != null)
			new PError("var '" + name + "' is already declared");

		Data<?> data = interpret(in.expression);
		String value = "" + data.asDouble(); //TODO naja, weiss nich

		//entry in symboltable
		VariableEntry var = new VariableEntry(name, DataType.DOUBLE, value);
		symbolTableManager.addVariableToScope(name, var);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretVariableNode() {
		VariableNode vn = (VariableNode) curInstruction;
		String varName = vn.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = Double.parseDouble(var.getValue());
		return new Data<Double>(value, DataType.DOUBLE);
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

	//!	//TODO unused vars?!?
	private Data<?> interpretForNode() {
		ForNode fn = (ForNode) curInstruction;

		symbolTableManager.newScope("if-initVar");
		Data<?> data = interpret(fn.init);

		double cond = interpret(fn.condition).asDouble();

		while (cond == 1) {
			interpret(fn.block);
			interpret(fn.assignment);
			cond = interpret(fn.condition).asDouble();
		}
		symbolTableManager.endScope();

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretEqualAssignNode() {
		AssignNode an = (AssignNode) curInstruction;
		String varName = an.getName();

		Data<?> data = interpret(an.expression);
		String value = "" + data.asDouble(); //TODO naja, weiss nich

		//try to find and get the variable from the SymbolTable
		VariableEntry ve = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (ve == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		ve.value = value;

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretIncrementNode() {
		IncrementNode in = (IncrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = Double.parseDouble(var.getValue());
		var.value = "" + (value + 1);
		value = Double.parseDouble(var.getValue());

		return new Data<Double>(value, DataType.DOUBLE);
	}

	private Data<?> interpretLateIncrementNode() {
		LateIncrementNode in = (LateIncrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = Double.parseDouble(var.getValue());
		var.value = "" + (value + 1);

		return new Data<Double>(value, DataType.DOUBLE);
	}

	private Data<?> interpretDecrementNode() {
		DecrementNode in = (DecrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = Double.parseDouble(var.getValue());
		var.value = "" + (value - 1);
		value = Double.parseDouble(var.getValue());

		return new Data<Double>(value, DataType.DOUBLE);
	}

	private Data<?> interpretAddAssignNode() {
		AddAssignNode man = (AddAssignNode) curInstruction;

		String varName = man.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		Data<?> data = interpret(man.expression);
		double increment = data.asDouble();

		double value = Double.parseDouble(var.getValue());

		var.value = "" + (value + increment);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretSubAssignNode() {
		SubAssignNode man = (SubAssignNode) curInstruction;

		String varName = man.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		Data<?> data = interpret(man.expression);
		double increment = data.asDouble();

		double value = Double.parseDouble(var.getValue());

		var.value = "" + (value - increment);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretMulAssignNode() {
		MulAssignNode man = (MulAssignNode) curInstruction;

		String varName = man.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		Data<?> data = interpret(man.expression);
		double increment = data.asDouble();

		double value = Double.parseDouble(var.getValue());

		var.value = "" + (value * increment);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretDivAssignNode() {
		DivAssignNode man = (DivAssignNode) curInstruction;

		String varName = man.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		Data<?> data = interpret(man.expression);
		double increment = data.asDouble();

		double value = Double.parseDouble(var.getValue());

		var.value = "" + (value / increment);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretModAssignNode() {
		ModAssignNode man = (ModAssignNode) curInstruction;

		String varName = man.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		Data<?> data = interpret(man.expression);
		double increment = data.asDouble();

		double value = Double.parseDouble(var.getValue());

		var.value = "" + (value % increment);

		return new Data<>(1, DataType.INT);
	}

	private Data<?> interpretLateDecrementNode() {
		LateDecrementNode in = (LateDecrementNode) curInstruction;

		String varName = in.getName();

		VariableEntry var = (VariableEntry) symbolTableManager.findVariableInScope(varName);
		if (var == null)
			new PError("Parse Error: var '" + varName + "' not declared");

		double value = Double.parseDouble(var.getValue());
		var.value = "" + (value - 1);

		return new Data<Double>(value, DataType.DOUBLE);
	}

	public SymbolTableManager getSymbolTableManager() {
		return symbolTableManager;
	}

	/**
	 * This is a method that has to be called, while the interpeter is working
	 */
	//TODO currently not working bcs of hashmap impl.
	public void printSymbolTable() {
		//	symbolTableManager.print();
	}

}