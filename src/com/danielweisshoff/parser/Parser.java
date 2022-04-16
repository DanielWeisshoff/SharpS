package com.danielweisshoff.parser;

import java.util.Stack;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.*;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.*;
import com.danielweisshoff.parser.nodesystem.node.data.*;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.*;
import com.danielweisshoff.parser.nodesystem.node.data.numbers.FloatingPointNumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.numbers.IntegerNumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.shortcuts.*;
import com.danielweisshoff.parser.nodesystem.node.logic.*;
import com.danielweisshoff.parser.nodesystem.node.logic.bitwise.BitWiseOrNode;
import com.danielweisshoff.parser.nodesystem.node.logic.bitwise.BitwiseAndNode;
import com.danielweisshoff.parser.nodesystem.node.loops.*;
import com.danielweisshoff.parser.semantic.ConversionChecker;

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

		switch (curToken.type()) {
		case KW_IF -> instruction = parseIf();
		case KW_ELSE -> instruction = parseElse();
		case KW_ELIF -> instruction = parseElif();
		//PRIMITIVES
		case KW_BYTE -> instruction = parseVariableDeclaration();
		case KW_SHORT -> instruction = parseVariableDeclaration();
		case KW_INT -> instruction = parseVariableDeclaration();
		case KW_LONG -> instruction = parseVariableDeclaration();
		case KW_FLOAT -> instruction = parseVariableDeclaration();
		case KW_DOUBLE -> instruction = parseVariableDeclaration();
		//
		case KW_WHILE -> instruction = parseWhile();
		case KW_FOR -> instruction = parseFor();
		case KW_DO -> instruction = parseDoWhile();
		case IDENTIFIER -> identifierStuff();
		case PLUS -> instruction = parsePreIncrement(); //++ increment
		case MINUS -> instruction = parsePreDecrement(); //-- decrement
		default -> new PError("[PARSER] Action for Token " + curToken.type() + " not implemented");
		}

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

	private void identifierStuff() {
		advance();

		if (is(TokenType.O_ROUND_BRACKET)) { //FNC
			retreat();
			instruction = parseFunctionCall();
		} else if (curToken.isOP() || is(TokenType.EQUAL) || is(TokenType.PERCENT)) { //VAR
			retreat();
			instruction = parseVariableInitialization();
		} else
			error = true;
	}

	private void printParseError() {
		String tokenList = "";
		for (Token t : tokens)
			tokenList += t.getDescription() + "\n";
		new PError("Parsing error. Unknown instruction:\n" + tokenList);
	}

	//returns the latest IfNode in the present scope
	//that doesnt have an else-block
	private IfNode findIfWithoutElseBlock(BlockNode n) {
		//get the latest IfNode from current scope
		IfNode in = null;
		for (int i = n.children.size() - 1; i >= 0; i--)
			if (n.children.get(i) instanceof IfNode) {
				in = (IfNode) n.children.get(i);
				break;
			}
		if (in == null)
			new PError("else statement doesnt have corresponding if");

		return in.elseBlock == null ? in : findIfWithoutElseBlock(in.elseBlock);
	}

	private void scopeOutIfNeeded() {
		int curScope = 0;
		if (is(TokenType.TAB)) {
			curScope = Integer.parseInt(curToken.value);
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

	public void retreat() {
		retreat(1);
	}

	public void retreat(int times) {
		for (int i = 0; i < times; i++)
			position--;

		if (position > -1)
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

	public void assume(TokenType t, String value, String error) {
		if (is(t) && is(value))
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
		assume(TokenType.KW_IF, "Keyword IF missing");
		assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");

		ConditionNode condition = parsePredicate();

		assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
		assume(TokenType.COLON, "if-block missing");

		IfNode in = new IfNode();
		in.condition = condition;
		lateScopeIn(in.condBlock);

		Logger.log("found condition");
		return in;
	}

	private IfNode parseElif() {
		assume(TokenType.KW_ELIF, "Keyword ELIF missing");

		IfNode in = findIfWithoutElseBlock(scopeNode.peek());
		in.elseBlock = new BlockNode();

		assume(TokenType.O_ROUND_BRACKET, "Parameterlist not found");

		ConditionNode condition = parsePredicate();

		assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");
		assume(TokenType.COLON, "elif-block missing");

		IfNode elif = new IfNode();
		elif.condition = condition;
		lateScopeIn(elif.condBlock);

		Logger.log("found condition");
		in.elseBlock.add(elif);

		scopeIn(elif.condBlock);
		addInstruction = false;

		return elif;
	}

	private BlockNode parseElse() {
		assume(TokenType.KW_ELSE, "Keyword ELSE missing");
		IfNode in = findIfWithoutElseBlock(scopeNode.peek());

		assume(TokenType.COLON, "unknown syntax for else statement");

		in.elseBlock = new BlockNode();
		scopeIn(in.elseBlock);
		addInstruction = false;

		return new BlockNode();
	}

	private ConditionNode parsePredicate() {
		ConditionNode left = parseCondition();

		ConditionNode cn = null;

		while (is(TokenType.AND) || is(TokenType.OR)) {
			if (is(TokenType.AND)) {
				advance();
				if (is(TokenType.AND)) {// &&
					advance();
					cn = new BooleanAndNode();
				} else // &
					cn = new BitwiseAndNode();
			} else if (is(TokenType.OR)) {
				advance();
				if (is(TokenType.OR)) {// ||
					advance();
					cn = new BooleanOrNode();
				} else // |
					cn = new BitWiseOrNode();
			}
			ConditionNode right = parseCondition();
			cn.left = left;
			cn.right = right;
			left = cn;
		}

		return left;
	}

	private ConditionNode parseCondition() {
		if (is(TokenType.O_ROUND_BRACKET)) {
			advance();

			ConditionNode n = parsePredicate();

			assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");
			return n;
		}

		Node leftExpr = parseExpression();
		Token compareType = curToken;
		advance();
		Node rightExpr = parseExpression();

		Logger.log("Gleichung erstellt");

		return switch (compareType.value) {
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
			advance();
			if (curToken.isLineOP()) { // increment /decrement
				retreat(2);
				break;
			}
			retreat();

			if (curToken.type() == TokenType.PLUS)
				op = new BinaryAddNode();
			else if (curToken.type() == TokenType.MINUS)
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
		while (curToken.isDotOP() || curToken.type() == TokenType.PERCENT) {

			if (is(TokenType.STAR))
				op = new BinaryMulNode();
			else if (is(TokenType.SLASH))
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

	//TODO spaghetti, incr,decr woanders parsen?
	private Node parseFactor() {
		int sign = 1;

		//? Ternary / --x	
		if (is(TokenType.MINUS)) {
			advance();
			if (is(TokenType.MINUS)) {
				retreat();
				return parsePreDecrement();
			} else
				sign = -1;
		}
		//? ++x 
		else if (is(TokenType.PLUS)) {
			advance();
			assume(TokenType.PLUS, "second incrementor + missing");
			retreat(2);

			AssignNode an = parsePreIncrement();

			return an;
		}
		//TODO integers
		//? INTEGER
		else if (is(TokenType.INTEGER)) {
			Node n = new IntegerNumberNode(Long.parseLong(curToken.value) * sign);
			advance();
			return n;
		}
		//?floating point numbers
		else if (is(TokenType.FLOATING_POINT)) {
			Node n = new FloatingPointNumberNode(Double.parseDouble(curToken.value) * sign);
			advance();
			return n;
		}
		//? Var
		else if (is(TokenType.IDENTIFIER)) {
			String varName = curToken.value;
			advance();

			VariableNode n = new VariableNode(varName);

			if (is(TokenType.PLUS)) { // x++
				advance();
				if (!is(TokenType.PLUS)) {
					retreat();
					return n;
				}
				retreat(2);

				AssignNode an = parsePostIncrement();
				return an;
			} else if (is(TokenType.MINUS)) { // x--
				advance();
				if (!is(TokenType.MINUS)) {
					retreat();
					return n;
				}
				retreat(2);

				AssignNode an = parsePostDecrement();
				return an;
			}
			return n;
		}
		//? paranthesized expr 
		else if (is(TokenType.O_ROUND_BRACKET)) {
			advance();

			Node n = parseExpression();
			assume(TokenType.C_ROUND_BRACKET, "Expression error. Bracket not properly closed");

			return n;
		}
		return null;
	}

	private CallNode parseFunctionCall() {
		String name = curToken.value;
		assume(TokenType.IDENTIFIER, "Functionname missing");

		assume(TokenType.O_ROUND_BRACKET, "Parameterlist not opened");

		String params = parseParameters();

		assume(TokenType.C_ROUND_BRACKET, "Parameterlist not closed");

		Logger.log("Functioncall '" + name + "'(" + params + ")");

		CallNode cn = new CallNode(name);

		return cn;
	}

	/*
	 *erstmals nur placeholder
	 */
	private String parseParameters() {
		String params = "";

		while (!is(TokenType.C_ROUND_BRACKET)) {
			if (is(TokenType.INTEGER) || is(TokenType.IDENTIFIER)) {
				params += curToken.value;
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

	@Deprecated
	private InitNode parseVariableDeclaration() {
		TokenType keyword = curToken.type();
		advance();

		String varName = curToken.value;
		assume(TokenType.IDENTIFIER, "Fehler beim Initialisieren einer Variable");

		InitNode n;

		DataType type = null;
		switch (keyword) {
		case KW_BYTE -> type = DataType.BYTE;
		case KW_SHORT -> type = DataType.SHORT;
		case KW_INT -> type = DataType.INT;
		case KW_LONG -> type = DataType.LONG;
		case KW_FLOAT -> type = DataType.FLOAT;
		case KW_DOUBLE -> type = DataType.DOUBLE;
		default -> new PError("parser: unknown primitive type " + keyword);
		}

		if (is(TokenType.EQUAL)) {
			//Variable wird initialisiert
			advance();
			Node expr = parseExpression();

			Logger.log("Variable " + varName + " initialisiert");

			n = new InitNode(varName, type, expr);
		} else {
			//Variable wird deklariert
			n = new InitNode(varName, type);
			Logger.log("Variable " + varName + " deklariert");
		}
		return n;
	}

	@Deprecated
	private AssignNode parseVariableInitialization() {
		//increment
		if (is(TokenType.PLUS)) {
			advance();

			assume(TokenType.PLUS, "Incrementor + missing");
			retreat(2);

			return parsePreIncrement();
		}
		//decrement
		else if (is(TokenType.MINUS)) {
			advance();
			assume(TokenType.MINUS, "Decrementor - missing");
			retreat(2);

			return parsePreDecrement();
		}

		//variations of assignments
		else if (is(TokenType.IDENTIFIER)) {
			advance();

			//late increment	x++
			if (is(TokenType.PLUS)) {
				advance();
				if (is(TokenType.PLUS)) {
					retreat(2);
					return parsePostIncrement();
				}
				retreat();
			}
			//late decrement	x--
			else if (is(TokenType.MINUS)) {
				advance();
				if (is(TokenType.MINUS)) {
					retreat(2);
					return parsePostDecrement();
				}
				retreat();
			}
			//* HAS TO HAPPEN AFTER INCR/DECR 
			if (curToken.isOP() || curToken.type() == TokenType.PERCENT) {
				retreat();
				return parseBinaryAssignNode();
			}

			// x= EXPR
			//TODO complete bs
			retreat();
			String varName = curToken.value;
			assume(TokenType.IDENTIFIER, "var name for assignment missing");

			if (is(TokenType.EQUAL))
				advance();
			else
				retreat();

			Node expr = parseExpression();

			EqualAssignNode an = new EqualAssignNode(varName);
			an.expression = expr;

			Logger.log("Assigned value to variable '" + varName + "'");
			return an;
		}
		return null; //TODO ka
	}

	//
	//LOOPS
	//

	private WhileNode parseWhile() {
		assume(TokenType.KW_WHILE, "Keyword WHILE missing");
		assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

		ConditionNode cn = parsePredicate();

		assume(TokenType.C_ROUND_BRACKET, "Missing close bracket for while-loop");
		assume(TokenType.COLON, "while-body not defined");

		WhileNode wn = new WhileNode();
		wn.condition = cn;
		wn.whileBlock = new BlockNode();

		lateScopeIn(wn.whileBlock);

		return wn;
	}

	private DoWhileNode parseDoWhile() {
		assume(TokenType.KW_DO, "Keyword DO missing");
		WhileNode wn = parseWhile();

		DoWhileNode dwn = new DoWhileNode();
		dwn.condition = wn.condition;
		dwn.whileBlock = wn.whileBlock;

		return dwn;
	}

	//TODO this needs a major overhaul someday when more variations will be added
	private ForNode parseFor() {
		assume(TokenType.KW_FOR, "Keyword FOR missing");
		assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for for-loop");

		InitNode in = parseVariableDeclaration();

		assume(TokenType.COMMA, "Comma missing");

		ConditionNode cn = parsePredicate();

		assume(TokenType.COMMA, "Comma missing");

		AssignNode an = parseVariableInitialization();

		assume(TokenType.C_ROUND_BRACKET, "Missing closed bracket for for-loop");
		assume(TokenType.COLON, "if-body-declarator missing");

		ForNode fn = new ForNode();
		fn.init = in;
		fn.condition = cn;
		fn.assignment = an;
		fn.block = new BlockNode();

		lateScopeIn(fn.block);
		return fn;
	}

	private AssignNode parsePostIncrement() {
		String varName = curToken.value;
		assume(TokenType.IDENTIFIER, "increment-assignment var missing");

		assume(TokenType.PLUS, "Incrementor + missing");
		assume(TokenType.PLUS, "Incrementor + missing");

		PostIncrementNode lin = new PostIncrementNode(varName);
		lin.variable = new VariableNode(varName);

		return lin;
	}

	private AssignNode parsePostDecrement() {
		String varName = curToken.value;
		assume(TokenType.IDENTIFIER, "decrement-assignment var missing");

		assume(TokenType.MINUS, "Decrementor - missing");
		assume(TokenType.MINUS, "Decrementor - missing");

		PostDecrementNode ldn = new PostDecrementNode(varName);
		ldn.variable = new VariableNode(varName);

		return ldn;

	}

	private AssignNode parsePreIncrement() {
		assume(TokenType.PLUS, "Incrementor + missing");
		assume(TokenType.PLUS, "Incrementor + missing");

		String varName = curToken.value;
		assume(TokenType.IDENTIFIER, "increment-assignment var missing");

		PreIncrementNode in = new PreIncrementNode(varName);
		in.variable = new VariableNode(varName);

		return in;
	}

	private AssignNode parsePreDecrement() {
		assume(TokenType.MINUS, "Decrementor - missing");
		assume(TokenType.MINUS, "Decrementor - missing");

		String varName = curToken.value;
		assume(TokenType.IDENTIFIER, "decrement-assignment var missing");

		PreDecrementNode dn = new PreDecrementNode(varName);
		dn.variable = new VariableNode(varName);

		return dn;

	}

	private AssignNode parseBinaryAssignNode() {
		String varName = curToken.value;
		assume(TokenType.IDENTIFIER, "var missing");

		BinaryOperationNode bon = null;
		switch (curToken.type()) {
		case PLUS -> bon = new BinaryAddNode();
		case MINUS -> bon = new BinarySubNode();
		case STAR -> bon = new BinaryMulNode();
		case SLASH -> bon = new BinaryDivNode();
		case PERCENT -> bon = new BinaryModNode();
		default -> new PError("Unknown assignment syntax '" + curToken.type() + "='");
		}
		advance();
		assume(TokenType.EQUAL, "AddAssign err");

		Node expr = parseExpression();

		EqualAssignNode ean = new EqualAssignNode(varName);

		VariableNode vn = new VariableNode(varName);
		bon.left = vn;
		bon.right = expr;

		ean.expression = bon;
		// AddAssignNode man = new AddAssignNode(varName);
		// man.expression = expr;

		return ean;
	}

	/**
	 * Vergleicht den aktuellen Token
	 */
	public boolean is(TokenType type) {
		return curToken.type() == type;
	}

	public boolean is(String value) {
		return is(value);
	}

	public BlockNode getAST() {
		return root;
	}
}