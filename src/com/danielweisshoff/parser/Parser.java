package com.danielweisshoff.parser;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.interpreter.builtin.BuiltInVariable;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.builders.VariableBuilder;
import com.danielweisshoff.parser.nodesystem.node.*;

/*TODO
 * - Entries k�nnen auch Namen haben
 * - Einen Weg finden, Methoden mit gleichen Namen aber unterschiedlichen Parametern zu speichern
 * - Dictionary<String,MethodGroup>
 */

/**
 * Converts tokens to a runnable AST
 */
public class Parser {

	private RootNode root = new RootNode();
	private ClassNode currentClass;
	private Node currentNode = root;

	private Token[] tokens;

	public Token curToken;
	private int position = -1;

	public Parser() {
		BuiltInFunction.registerAll();
		BuiltInVariable.registerAll();
	}

	public void parseLine(Token[] tokens) {
		this.tokens = tokens;
		position = -1;
		advance();

		if (curToken.type() == TokenType.KEYWORD)
			VariableBuilder.buildVariable(this);

		if (curToken.type() == TokenType.IDENTIFIER) {
			String name = curToken.getValue();
			advance();

			if (curToken.type() == TokenType.O_ROUND_BRACKET) {
				CallNode cn = new CallNode(name);
			}
		}

		//TODO Erstmal unwichtig
		// if (is(TokenType.TAB)) {
		// 	String tabs = currentToken.getValue();
		// 	System.out.println(tabs);
		// 	advance();

		// 	if (tabs.equals("1"))
		// 		validateAttributeLane();
		// 	else if (tabs.equals("2"))
		// 		validateFunctionLane();
		// 	else
		// 		validateScope();
		// } else //Ansonsten gehen wir von einer Klassendefinition aus
		// 	validateClassLane();
	}

	public void advance() {
		position++;
		if (position < tokens.length)
			curToken = tokens[position];
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