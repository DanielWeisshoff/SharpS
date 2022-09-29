package com.danielweisshoff.interpreter;

import java.util.Scanner;

import com.danielweisshoff.interpreter.builtin.functions.BuiltInFunction;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.CallNode;
import com.danielweisshoff.parser.nodesystem.node.IfNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.BinaryOperationNode;
import com.danielweisshoff.parser.nodesystem.node.data.PointerNode;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.VarInitNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PostDecrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PostIncrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PreDecrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.shortcuts.PreIncrementNode;
import com.danielweisshoff.parser.nodesystem.node.data.primitives.PrimitiveNode;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;
import com.danielweisshoff.parser.nodesystem.node.loops.DoWhileNode;
import com.danielweisshoff.parser.nodesystem.node.loops.ForNode;
import com.danielweisshoff.parser.nodesystem.node.loops.WhileNode;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/*
TODO
?pointernode id und name werden komisch eingetragen

TODO
Scopes komplett neu aufsetzen
TODO

*/
public class Interpreter {

    public static boolean debug = false;
    //Variables and Functions
    public static SymbolTableManager stm = new SymbolTableManager();
    public static TypeConversion conversionChecker = new TypeConversion(stm);
    private Node curInstruction;
    //when stepper is enabled, one line will be interpreted at a time on user input
    private boolean stepFinished = false;
    private Scanner stepperScanner = new Scanner(System.in);

    // public Interpreter() {
    //     stm.deleteTableOnScopeEnd = true;
    // }

    public Interpreter() {
        stm.deleteTableOnScopeEnd = true;
    }

    public Data interpret(Node n) {
        return n.run();
    }

    // public Data interpret(Node n) {
    // 	curInstruction = n;
    // 	//this is the return-value of the instruction
    // 	Data data = null;

    // 	switch (n.nodeType) {
    // 	case BLOCK_NODE -> data = interpretBlockNode();
    // 	case CALL_NODE -> data = interpretCallNode();
    // 	case IF_NODE -> data = interpretIfNode();
    // 	//ARITHMETIC
    // 	case BINARY_ADD_NODE -> data = interpretBinaryOperationNode('+');
    // 	case BINARY_SUB_NODE -> data = interpretBinaryOperationNode('-');
    // 	case BINARY_MUL_NODE -> data = interpretBinaryOperationNode('*');
    // 	case BINARY_DIV_NODE -> data = interpretBinaryOperationNode('/');
    // 	case BINARY_MOD_NODE -> data = interpretBinaryOperationNode('%');
    // 	//PRIMITIVES (type safety)
    // 	case BYTE_NODE -> data = interpretPrimitiveNode();
    // 	case SHORT_NODE -> data = interpretPrimitiveNode();
    // 	case INTEGER_NODE -> data = interpretPrimitiveNode();
    // 	case LONG_NODE -> data = interpretPrimitiveNode();
    // 	case FLOAT_NODE -> data = interpretPrimitiveNode();
    // 	case DOUBLE_NODE -> data = interpretPrimitiveNode();
    // 	case POINTER_NODE -> data = interpretPointerNode();
    // 	//INCREMENT / DECREMENT
    // 	case PRE_INCREMENT_NODE -> data = interpretPreIncrementNode();
    // 	case POST_INCREMENT_NODE -> data = interpretPostIncrementNode();
    // 	case PRE_DECREMENT_NODE -> data = interpretPreDecrementNode();
    // 	case POST_DECREMENT_NODE -> data = interpretPostDecrementNode();
    // 	//CONDITIONS
    // 	case LESS_NODE -> data = interpretConditionNode("<");
    // 	case LESS_EQUAL_NODE -> data = interpretConditionNode("<=");
    // 	case MORE_NODE -> data = interpretConditionNode(">");
    // 	case MORE_EQUAL_NODE -> data = interpretConditionNode(">=");
    // 	case EQUAL_NODE -> data = interpretConditionNode("==");
    // 	case NOT_EQUAL_NODE -> data = interpretConditionNode("!=");
    // 	case BOOLEAN_AND_NODE -> data = interpretConditionNode("&&");
    // 	case BOOLEAN_OR_NODE -> data = interpretConditionNode("||");
    // 	case BITWISE_AND_NODE -> data = interpretConditionNode("&");
    // 	case BITWISE_OR_NODE -> data = interpretConditionNode("|");
    // 	//VAR STUFF
    // 	case DECLARE_NODE -> data = interpretDeclareNode();
    // 	case INIT_NODE -> data = interpretInitNode();
    // 	case VARIABLE_NODE -> data = interpretVariableNode();
    // 	//LOOPS
    // 	case WHILE_NODE -> data = interpretWhileNode();
    // 	case DO_WHILE_NODE -> data = interpretDoWhileNode();
    // 	case FOR_NODE -> data = interpretForNode();
    // 	default -> {
    // 		System.out.println("[INTERPRETER]: visited unknown Node type " + n.getClass().getSimpleName());
    // 		System.exit(0);
    // 	}
    //}
    //
    //	//Debug Mode
    //if(debug&&stepFinished)
    //	{System.out.println("*** PRESS ENTER TO CONTINUE ***");stepperScanner.nextLine();stepFinished=false;}return data;}

    // private Data interpretBlockNode() {
    // 	BlockNode bn = (BlockNode) curInstruction;

    // 	for (Node n : bn.children)
    // 		interpret(n);

    // 	return new Data();
    // }

    // private Data interpretCallNode() {
    // 	CallNode cn = (CallNode) curInstruction;
    // 	String fName = cn.getName();

    // 	if (BuiltInFunction.builtInFunctions.containsKey(fName)) {
    // 		Logger.log("BuiltInFunktion " + fName + " wird aufgerufen");
    // 		return BuiltInFunction.builtInFunctions.get(fName).call();
    // 	}
    // 	return new Data();
    // }

    // private Data interpretIfNode() {
    // 	IfNode in = (IfNode) curInstruction;

    // 	Data cond = interpret(in.condition);

    // 	if (cond.asInt() == 1)
    // 		interpret(in.condBlock);
    // 	else if (in.elseBlock != null)
    // 		interpret(in.elseBlock);

    // 	return new Data();
    // }

    // private Data interpretBinaryOperationNode(char op) {
    // 	BinaryOperationNode ban = (BinaryOperationNode) curInstruction;

    // 	double left = interpret(ban.left).asDouble();
    // 	double right = interpret(ban.right).asDouble();
    // 	return switch (op) {
    // 	case '+' -> new Data(left + right, DataType.DOUBLE);
    // 	case '-' -> new Data(left - right, DataType.DOUBLE);
    // 	case '*' -> new Data(left * right, DataType.DOUBLE);
    // 	case '/' -> new Data(left / right, DataType.DOUBLE);
    // 	case '%' -> new Data(left % right, DataType.DOUBLE);
    // 	default -> new Data();
    // 	};
    // }

    // private Data interpretPrimitiveNode() {
    // 	PrimitiveNode pn = (PrimitiveNode) curInstruction;
    // 	return pn.getData();
    // }

    // //gets the id of the variable the pointer points to
    // private Data interpretPointerNode() {
    // 	PointerNode pn = (PointerNode) curInstruction;

    // 	VariableEntry ve = symbolTable.findVariable(pn.variable);
    // 	if (ve == null)
    // 		new UnimplementedError("cant point to undeclared var '" + pn.variable + "'");

    // 	long id = ve.getID();

    // 	return new Data(id, DataType.POINTER);
    // }

    // private Data interpretConditionNode(String op) {
    // 	ConditionNode cn = (ConditionNode) curInstruction;

    // 	double left = interpret(cn.left).asDouble();
    // 	double right = interpret(cn.right).asDouble();

    // 	boolean cond;
    // 	switch (op) {
    // 	case "<" -> cond = left < right;
    // 	case "<=" -> cond = left <= right;
    // 	case ">" -> cond = left > right;
    // 	case ">=" -> cond = left >= right;
    // 	case "==" -> cond = left == right;
    // 	case "!=" -> cond = left != right;
    // 	case "&&" -> cond = (left == 1 && right == 1) ? true : false;
    // 	case "||" -> cond = (left == 1 || right == 1) ? true : false;
    // 	case "&" -> cond = (left == 1 & right == 1) ? true : false;
    // 	case "|" -> cond = (left == 1 | right == 1) ? true : false;
    // 	default -> {
    // 		cond = false;
    // 		System.out.println("Unknown condition '" + op + "'");
    // 		System.exit(0);
    // 	}
    // 	}

    // 	int stmt = cond ? 1 : 0;
    // 	return new Data(stmt, DataType.BOOLEAN);
    // }

    // private Data interpretDeclareNode() {
    // 	DeclareNode in = (DeclareNode) curInstruction;

    // 	String name = in.getName();

    // 	//schauen, ob variable schon existiert
    // 	if (symbolTable.lookupVariable(name))
    // 		new UnimplementedError("var '" + name + "': " + in.dataType + " is already declared");

    // 	//TODO move to semantics
    // 	conversionChecker.convert(in.dataType, in.expression);

    // 	Data data = interpret(in.expression);

    // 	//generate an id for the variable
    // 	long id = IdRegistry.newID();

    // 	//if the expression is a pointer
    // 	if (data.dataType == DataType.POINTER) {
    // 		VariableEntry ve = symbolTable.findVariable(name);
    // 		//entry in symboltable
    // 		VariableEntry var = new VariableEntry(name, id, in.dataType, ve.data);
    // 		symbolTable.addVariable(id, var);
    // 	} else {
    // 		//entry in symboltable
    // 		VariableEntry var = new VariableEntry(name, id, in.dataType, data);
    // 		symbolTable.addVariable(id, var);
    // 	}

    // 	return new Data();
    // }

    // private Data interpretInitNode() {
    // 	InitNode an = (InitNode) curInstruction;
    // 	String varName = an.getName();

    // 	Data data = interpret(an.expression);

    // 	if (data.dataType == DataType.POINTER) {
    // 		VariableEntry ve = symbolTable.findVariable(varName);
    // 		//entry in symboltable
    // 		VariableEntry ve2 = symbolTable.findVariable(varName);
    // 		ve2.data = ve.data;

    // 		return new Data();
    // 	} else {

    // 		double value = data.asDouble();

    // 		//try to find and get the variable from the SymbolTable
    // 		VariableEntry ve = symbolTable.findVariable(varName);
    // 		if (ve == null)
    // 			new UnimplementedError("Interpreter Error: var '" + varName + "' not declared");

    // 		//da referenzen geloescht werden
    // 		switch (ve.dataType) {
    // 		case BYTE -> ve.data.setValue((byte) value);
    // 		case SHORT -> ve.data.setValue((short) value);
    // 		case INT -> ve.data.setValue((int) value);
    // 		case LONG -> ve.data.setValue((long) value);
    // 		case FLOAT -> ve.data.setValue((float) value);
    // 		case DOUBLE -> ve.data.setValue((double) value);
    // 		default -> {
    // 			System.out.println("unimplemented action for datatype " + ve.dataType);
    // 			System.exit(0);
    // 		}
    // 		}

    // 		if (debug)
    // 			System.out.println(ve.data.data + ", " + ve.dataType);

    // 		return new Data();
    // 	}
    // }

    // private Data interpretVariableNode() {
    // 	VariableNode vn = (VariableNode) curInstruction;
    // 	String varName = vn.getName();

    // 	VariableEntry var = symbolTable.findVariable(varName);
    // 	if (var == null)
    // 		new UnimplementedError("Interpreter Error: var '" + varName + "' not declared");

    // 	return var.getData();
    // }

    // private Data interpretWhileNode() {
    // 	WhileNode wn = (WhileNode) curInstruction;

    // 	symbolTable.newScope("while body");
    // 	while (interpret(wn.condition).asDouble() == 1) {
    // 		interpret(wn.whileBlock);
    // 		symbolTable.clearCurrentTable();
    // 	}
    // 	symbolTable.endScope();

    // 	return new Data();
    // }

    // private Data interpretDoWhileNode() {
    // 	DoWhileNode dwn = (DoWhileNode) curInstruction;

    // 	symbolTable.newScope("do-while body");

    // 	interpret(dwn.whileBlock);

    // 	while (interpret(dwn.condition).asDouble() == 1) {
    // 		symbolTable.clearCurrentTable();
    // 		interpret(dwn.whileBlock);
    // 	}

    // 	symbolTable.endScope();

    // 	return new Data();
    // }

    // private Data interpretForNode() {
    // 	ForNode fn = (ForNode) curInstruction;

    // 	symbolTable.newScope("for-init");
    // 	interpret(fn.init);

    // 	symbolTable.newScope("for-body");
    // 	double cond = interpret(fn.condition).asDouble();
    // 	while (cond == 1) {
    // 		interpret(fn.block);
    // 		interpret(fn.assignment);
    // 		cond = interpret(fn.condition).asDouble();
    // 		symbolTable.clearCurrentTable();
    // 	}
    // 	symbolTable.endScope(); //body scope
    // 	symbolTable.endScope(); //init scope
    // 	return new Data();
    // }

    // //TODO remove
    // private Data interpretPreIncrementNode() {
    // 	PreIncrementNode in = (PreIncrementNode) curInstruction;

    // 	String varName = in.getName();

    // 	VariableEntry var = symbolTable.findVariable(varName);
    // 	if (var == null)
    // 		new UnimplementedError("Interpreter Error: var '" + varName + "' not declared");

    // 	double value = var.getData().asDouble();
    // 	var.data.setValue(value + 1);

    // 	return var.data;
    // }

    // //TODO remove
    // private Data interpretPreDecrementNode() {
    // 	PreDecrementNode in = (PreDecrementNode) curInstruction;

    // 	String varName = in.getName();

    // 	VariableEntry var = symbolTable.findVariable(varName);
    // 	if (var == null)
    // 		new UnimplementedError("Interpreter Error: var '" + varName + "' not declared");

    // 	double value = var.getData().asDouble();
    // 	var.data.setValue(value - 1);

    // 	return var.data;
    // }

    // //TODO remove
    // private Data interpretPostIncrementNode() {
    // 	PostIncrementNode in = (PostIncrementNode) curInstruction;

    // 	String varName = in.getName();

    // 	VariableEntry var = symbolTable.findVariable(varName);
    // 	if (var == null)
    // 		new UnimplementedError("Interpreter Error: var '" + varName + "' not declared");

    // 	double value = var.getData().asDouble();
    // 	var.data.setValue(value + 1);

    // 	return new Data(value, DataType.DOUBLE);
    // }

    // //TODO remove
    // private Data interpretPostDecrementNode() {
    // 	PostDecrementNode in = (PostDecrementNode) curInstruction;

    // 	String varName = in.getName();

    // 	VariableEntry var = symbolTable.findVariable(varName);
    // 	if (var == null)
    // 		new UnimplementedError("Interpreter Error: var '" + varName + "' not declared");

    // 	double value = var.getData().asDouble();
    // 	var.data.setValue(value - 1);

    // 	return new Data(value, DataType.DOUBLE);
    // }

    // public SymbolTableManager getSymbolTableManager() {
    // 	return symbolTable;
    // }

    // //TODO currently not working bcs of hashmap impl.
    // // public void printSymbolTable() {
    // //		if(debug)
    // //	symbolTable.print();
    // // }

}