package com.danielweisshoff.parser;

import java.util.Stack;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.node.*;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.*;
import com.danielweisshoff.parser.nodesystem.node.data.*;
import com.danielweisshoff.parser.nodesystem.node.logic.*;
import com.danielweisshoff.parser.nodesystem.node.loops.ForNode;
import com.danielweisshoff.parser.nodesystem.node.loops.WhileNode;

/**
 * Converts tokens to a runnable AST
 */
public class Parser {

	//?change to RootNode later?
	private BlockNode root = new BlockNode();

	private Token[] tokens;

	public Token curToken;
	private int position = -1;

	private boolean error = false;
	private Node instruction;

	//For block building
	private Stack<BlockNode> scopeNode = new Stack<>();
	private int scopeDepth = -1;

	//some quirky code for scoping
	private boolean lateScopeIn = false;
	private BlockNode scopeInBlock;
	private boolean addInstruction = true;

	public Parser() {
		BuiltInFunction.registerAll();
		root = new BlockNode();
		scopeDepth = 0;
		scopeNode.add(root);
	}

	/**
	 * Assumes it gets a non empty array
	 * @param tokens
	 */
	public void parseLine(Token[] tokens) {
		this.tokens = tokens;
		position = -1;
		advance();

		instruction = null;
		addInstruction = true;

		//if tab count is reduced, scope out
		scopeOutIfNeeded();

		if (is(TokenType.KEYWORD)) {
			String value = curToken.getValue();
			advance();

			switch (value) {
			case "IF" -> instruction = parseIf();
			case "ELSE" -> instruction = parseElse();
			case "ELIF" -> instruction = parseElif();
			//ALL PRIMITIVES
			case "INT" -> instruction = parseVariable("INT");
			case "WHILE" -> instruction = parseWhile();
			case "FOR" -> instruction = parseFor();
			default -> new PError("[PARSER] Action for keyword " + value + " not implemented");
			}
		}
		//FUNCTION
		else if (is(TokenType.IDENTIFIER)) {
			String name = curToken.getValue();
			advance();

			if (is(TokenType.O_ROUND_BRACKET)) {
				advance();
				instruction = parseFunctionCall(name);
			} else if (is(TokenType.ASSIGN)) {
				advance();
				instruction = parseVariableAssignment(name);
			} else
				error = true;
		} else
			error = true;

		/*
		*
		*/
		if (error)
			printParseError();

		//add instrucent to current scope
		if (addInstruction)
			scopeNode.peek().add(instruction);

		//could need an overhaul LMAO 
		if (lateScopeIn) {
			scopeDepth++;
			scopeNode.add(scopeInBlock);
			lateScopeIn = false;
		}
	}

	private void printParseError() {
		String tokenList = "";
		for (Token t : tokens)
			tokenList += t.getDescription() + "\n";
		new PError("Parsing error. Unknown instruction:\n" + tokenList);
	}

	//searches for the latest IfNode (if/elseif) in the present scope
	//and examines, if the statement already has an else block or not
	private IfNode findIfWithoutElse(BlockNode n) {
		//get the latest IfNode from current scope
		IfNode in = null;
		for (int i = n.children.size() - 1; i >= 0; i--)
			if (n.children.get(i) instanceof IfNode) {
				in = (IfNode) n.children.get(i);
				break;
			}
		if (in == null)
			new PError("else statement doesnt have corresponding if");

		return in.elseBlock == null ? in : findIfWithoutElse(in.elseBlock);
	}

	private void scopeOutIfNeeded() {
		int curScope = 0;
		if (is(TokenType.TAB)) {
			curScope = Integer.parseInt(curToken.getValue());
			advance();
		}
		//Rausscopen, falls noetig
		if (curScope < scopeDepth) {
			int diff = scopeDepth - (curScope);
			scopeOut(diff);
		}
	}

	private void lateScopeIn(BlockNode bn) {
		lateScopeIn = true;
		scopeInBlock = bn;
	}

	private void scopeIn(BlockNode bn) {
		scopeDepth++;
		scopeNode.add(bn);
	}

	private void scopeOut(int amount) {
		for (int i = 0; i < amount; i++) {
			scopeNode.pop();
			scopeDepth--;
		}
	}

	public void advance() {
		position++;
		if (position < tokens.length)
			curToken = tokens[position];
	}

	/**
	 * Check, if current token type equals r, if not print error
	 */
	public void assume(TokenType t, String error) {
		if (is(t))
			advance();
		else
			new PError(error);
	}

	public Token next() {
		if (position < tokens.length - 1)
			return tokens[position + 1];
		return new Token(TokenType.EOF, "");
	}

	public boolean next(TokenType t) {
		if (position < tokens.length - 1)
			return tokens[position + 1].type() == t;
		return false;
	}

	/*
	 * Parsing Methoden
	 * 
	 */

	private IfNode parseIf() {
		assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");
		ConditionNode condition = parseCondition();
		assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
		assume(TokenType.COLON, "if-block missing");

		IfNode in = new IfNode();
		in.condition = condition;
		lateScopeIn(in.condBlock);

		Logger.log("found condition");
		return in;
	}

	private BlockNode parseElse() {
		IfNode in = findIfWithoutElse(scopeNode.peek());

		assume(TokenType.COLON, "unknown syntax for else statement");

		in.elseBlock = new BlockNode();
		scopeIn(in.elseBlock);
		addInstruction = false;

		return new BlockNode();
	}

	private IfNode parseElif() {
		IfNode in = findIfWithoutElse(scopeNode.peek());
		in.elseBlock = new BlockNode();

		IfNode elif = parseIf();
		in.elseBlock.add(elif);

		scopeIn(elif.condBlock);
		addInstruction = false;

		return elif;
	}

	private ConditionNode parseCondition() {
		Node leftExpr = parseExpression();
		Token compareType = curToken;
		advance();
		Node rightExpr = parseExpression();

		Logger.log("Gleichung erstellt");

		return switch (compareType.getValue()) {
		case "<" -> new LessNode(leftExpr, rightExpr);
		case "<=" -> new LessEqualNode(leftExpr, rightExpr);
		case ">" -> new MoreNode(leftExpr, rightExpr);
		case ">=" -> new MoreEqualNode(leftExpr, rightExpr);
		case "==" -> new EqualNode(leftExpr, rightExpr);
		case "!=" -> new NotEqualNode(leftExpr, rightExpr);
		default -> null;
		};
	}

	private Node parseExpression() {
		Node left = parseTerm();

		BinaryOperationNode op = null;
		while (curToken.isLineOP()) {

			if (curToken.type() == TokenType.ADD)
				op = new BinaryAddNode();
			else if (curToken.type() == TokenType.SUB)
				op = new BinarySubNode();
			advance();

			Node right = parseTerm();

			op.left = left;
			op.right = right;
			left = op;
		}
		return left;
	}

	private Node parseTerm() {
		Node left = parseFactor();

		BinaryOperationNode op = null;
		while (curToken.isDotOP() || curToken.type() == TokenType.MOD) {

			if (is(TokenType.MUL))
				op = new BinaryMulNode();
			else if (is(TokenType.DIV))
				op = new BinaryDivNode();
			else
				op = new BinaryModNode();
			advance();

			Node right = parseFactor();

			op.left = left;
			op.right = right;
			left = op;
		}
		return left;
	}

	private Node parseFactor() {
		int isUnary = 1;

		if (is(TokenType.SUB)) {
			isUnary = -1;
			advance();
		}

		if (is(TokenType.NUMBER)) {
			Node n = new NumberNode(Integer.parseInt(curToken.getValue()) * isUnary);
			advance();
			return n;
		} else if (is(TokenType.IDENTIFIER)) {
			Node n = new VariableNode(curToken.getValue());
			advance();
			return n;
		} else if (is(TokenType.O_ROUND_BRACKET)) {
			advance();

			Node n = parseExpression();

			assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");

			return n;
		} else
			new PError("Expr error, couldnt build factor");
		return null;
	}

	private CallNode parseFunctionCall(String functionName) {
		String params = parseParameters();

		assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");

		Logger.log("Functioncall '" + functionName + "'(" + params + ")");

		CallNode cn = new CallNode(functionName);

		return cn;
	}

	/*
	 *erstmals nur placeholder
	 */
	private String parseParameters() {
		String params = "";

		while (!is(TokenType.C_ROUND_BRACKET)) {
			if (is(TokenType.NUMBER) || is(TokenType.IDENTIFIER)) {
				params += curToken.getValue();
				advance();
				if (is(TokenType.COMMA)) {
					params += ", ";
					advance();
				} else
					break;

			}
		}
		advance();

		return params;
	}

	//TODO keyword ist spaeter zum unterscheiden der primitiven da
	private InitNode parseVariable(String keyword) {

		if (keyword != "INT")
			new PError("[PARSER]: Unknown primitive type '" + keyword + "'");

		String varName = curToken.getValue();
		assume(TokenType.IDENTIFIER, "Fehler beim Initialisieren einer Variable");

		InitNode n;
		if (is(TokenType.ASSIGN)) {
			//Variable wird initialisiert

			advance();
			Node expr = parseExpression();

			Logger.log("Variable " + varName + " initialisiert");
			n = new InitNode(varName, expr);
		} else {
			//Variable wird deklariert
			n = new InitNode(varName);
			Logger.log("Variable " + varName + " deklariert");
		}
		return n;
	}

	private AssignNode parseVariableAssignment(String varName) {
		Node expr = parseExpression();

		AssignNode an = new AssignNode(varName, expr);

		Logger.log("Assigned value to variable '" + varName + "'");
		return an;
	}

	//
	//LOOPS
	//

	private WhileNode parseWhile() {
		assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

		ConditionNode cn = parseCondition();

		assume(TokenType.C_ROUND_BRACKET, "Missing close bracket for while-loop");
		assume(TokenType.COLON, "while-body not defined");

		WhileNode wn = new WhileNode();
		wn.condition = cn;
		wn.whileBlock = new BlockNode();

		lateScopeIn(wn.whileBlock);

		return wn;
	}

	//TODO this needs a major overhaul someday when more variations will be added
	private ForNode parseFor() {
		assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

		assume(TokenType.KEYWORD, "if-var init missing");
		InitNode in = parseVariable("INT");

		assume(TokenType.COMMA, "OOF");

		ConditionNode cn = parseCondition();

		assume(TokenType.COMMA, "OOF");

		if (curToken.type() != TokenType.IDENTIFIER)
			new PError("if-assign missing");
		String varName = curToken.getValue();
		advance();

		advance(); // '='

		AssignNode an = parseVariableAssignment(varName);

		ForNode fn = new ForNode();
		fn.init = in;
		fn.condition = cn;
		fn.assignment = an;
		fn.block = new BlockNode();

		lateScopeIn(fn.block);
		return fn;
	}

	/**
	 * Vergleicht den aktuellen Token
	 */
	public boolean is(TokenType type) {
		return curToken.type() == type;
	}

	public boolean is(String value) {
		return curToken.getValue().equals(value);
	}

	public BlockNode getAST() {
		return root;
	}
}