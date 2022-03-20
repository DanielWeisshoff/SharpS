package com.danielweisshoff.parser;

import java.util.Stack;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.builders.FunctionCallBuilder;
import com.danielweisshoff.parser.builders.IfBuilder;
import com.danielweisshoff.parser.builders.VariableBuilder;
import com.danielweisshoff.parser.nodesystem.node.*;

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

		Node instruction = new ErrorNode();
		addInstruction = true;

		int curScope = 0;
		if (curToken.type() == TokenType.TAB) {
			curScope = Integer.parseInt(curToken.getValue());
			advance();
		}
		//SCOPING
		//curScope duefte niemals groesser sein als scopeDepth
		if (curScope < scopeDepth) {
			int diff = scopeDepth - (curScope);
			scopeOut(diff);
		}

		//VARIABLE
		if (curToken.isPrimitive())
			instruction = VariableBuilder.buildVariable(this);

		//IF
		else if (curToken.type() == TokenType.KEYWORD && curToken.getValue().equals("IF")) {
			advance();
			IfNode in = IfBuilder.buildIf(this);
			instruction = in;
			lateScopeIn(in.condBlock);
		}
		//ELSE
		else if (curToken.type() == TokenType.KEYWORD && (curToken.getValue().equals("ELSE"))) {
			advance();

			IfNode in = findLatestIfNode(scopeNode.peek());
			if (in == null || in.elseBlock != null)
				new PError("the else statement doesnt have an if part");

			instruction = IfBuilder.buildElse(this);

			in.elseBlock = new BlockNode();
			scopeIn(in.elseBlock);
			addInstruction = false;
		}

		//TODO ELIF
		else if (curToken.type() == TokenType.KEYWORD && (curToken.getValue().equals("ELIF"))) {
			advance();

			IfNode in = findLatestIfNode(scopeNode.peek());
			if (in == null || in.elseBlock != null)
				new PError("the else statement doesnt have an if part");

			instruction = IfBuilder.buildIf(this);

			in.elseBlock = new BlockNode();
			in.elseBlock.add(instruction);
			scopeIn(((IfNode) instruction).condBlock);
			addInstruction = false;
		}

		//TODO FUNCTION
		else if (curToken.type() == TokenType.IDENTIFIER) {
			String name = curToken.getValue();
			advance();

			if (curToken.type() == TokenType.O_ROUND_BRACKET) {
				advance();
				instruction = FunctionCallBuilder.buildFunctionCall(this, name);
			} else if (curToken.type() == TokenType.ASSIGN)
				instruction = VariableBuilder.assignVariable(this, name);
			else
				error = true;
		} else
			error = true;

		if (error) {
			String tokenList = "";
			for (Token t : tokens)
				tokenList += t.getDescription() + "\n";
			new PError("Parsing error. Unknown instruction:\n" + tokenList);
		}

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

	//searches for the latest IfNode (if/elseif) in the present scope
	private IfNode findLatestIfNode(BlockNode n) {
		for (int i = n.children.size() - 1; i >= 0; i--) {
			if (n.children.get(i) instanceof IfNode) {
				BlockNode bn = ((IfNode) n.children.get(i)).elseBlock;
				if (bn != null) {
					IfNode in = findLatestIfNode(bn);
					if (in != null)
						return in;
				}
				return (IfNode) n.children.get(i);
			}
		}
		return null;
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
		if (curToken.type() == t)
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

	//OOP ZEUGS
	// /*
	//  *===================================================================
	//  *    VALIDATION   VALIDATION  VALIDATION   VALIDATION  VALIDATION
	//  *===================================================================
	//  */

	// public void validateClassLane() {
	// 	//Class, Enum, Interface, Struct
	// 	switch (curToken.getValue()) {
	// 		case "cls" -> {
	// 			Class c = ClassBuilder.buildClass(this);
	// 			ClassNode classNode = new ClassNode(c.getName());
	// 			root.addClass(classNode);
	// 			currentClass = classNode;
	// 		}
	// 	}
	// }

	// public void validateAttributeLane() {
	// 	//Hier koennen nur Attribute angelegt werden
	// 	if (is(TokenType.KEYWORD)) {
	// 		advance();
	// 		Node n = VariableBuilder.initializeVariable(this);
	// 		currentClass.addAttribute(n);
	// 	} else
	// 		new PError("Class Lane: Falsche Syntax");
	// }

	// public void validateFunctionLane() {
	// 	//hier koennen nur Funktionen angelegt werden
	// 	if (is(TokenType.KEYWORD)) {
	// 		Node function = FunctionBuilder.build(this);
	// 		currentClass.addFunction(function);
	// 		currentNode = function;
	// 	}
	// }

	// public void validateScope() {

	// }

	// public AST getAST() {
	// 	return new AST(root);
	// }
}
