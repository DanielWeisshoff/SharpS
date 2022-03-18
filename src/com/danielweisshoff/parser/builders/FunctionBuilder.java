package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.PError;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.ConstructorNode;
import com.danielweisshoff.parser.nodesystem.node.EntryNode;
import com.danielweisshoff.parser.nodesystem.node.FunctionNode;
import com.danielweisshoff.parser.nodesystem.node.Node;

/* TODO
 *  - Da Methoden unver�nderbar sind und nicht erzeugt / gel�scht werden k�nnen,
 *   sollten sie direkt mit IDs aufgerufen werden. Dazu wird hier eine ArrayList<String,int> angegeben um
 *   eingegebene Methodennamen zu vergleichen und anschlie�end die daraus entstehende id anstatt des
 *   Namens in der CallNode eintragen.
 *  - Nach bearbeitung die Todolist in VariableBuilder kopieren
 */
public class FunctionBuilder {

	// private static int entryCounter;

	// public static Node build(Parser p) {
	// 	if (p.is("ntr"))
	// 		return buildEntry(p);
	// 	else if (p.is("con"))
	// 		return buildConstructor(p);
	// 	else
	// 		return buildFunction(p);
	// }

	// private static EntryNode buildEntry(Parser p) {
	// 	String functionName;

	// 	if (p.next().type() == TokenType.IDENTIFIER) {
	// 		p.advance();
	// 		functionName = p.curToken.getValue();
	// 	} else
	// 		functionName = "entry" + entryCounter++;

	// 	p.advance();
	// 	ParameterBuilder.buildParameters(p);
	// 	if (!p.is(TokenType.COLON))
	// 		new PError("Falsches Format");

	// 	Logger.log("Entry '" + functionName + "' erkannt");

	// 	EntryNode functionRoot = new EntryNode(functionName);

	// 	return functionRoot;
	// }

	// private static ConstructorNode buildConstructor(Parser p) {
	// 	String functionName = "constructor";

	// 	p.advance();
	// 	ParameterBuilder.buildParameters(p);
	// 	if (!p.is(TokenType.COLON))
	// 		new PError("Falsches Format");

	// 	Logger.log("Konstruktor '" + functionName + "' erkannt ");

	// 	return new ConstructorNode(functionName);
	// }

	private static FunctionNode buildFunction(Parser p) {
		String functionName = p.curToken.getValue();
		p.advance();
		ParameterBuilder.buildParameters(p);
		if (!p.is(TokenType.COLON))
			new PError("Body Declarator fehlt");

		Logger.log("Funktion '" + functionName + "' erkannt ");

		FunctionNode function = new FunctionNode(functionName);

		return function;
	}
}
