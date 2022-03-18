package com.danielweisshoff.parser.builders;

import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.CallNode;

/* TODO
 *  - Parameter sollen m�glich sein
 *  - Es soll geschaut werden, ob die Parameter richtig sind
 *  - Falls der R�ckgabewert gespeichert wird soll geschaut werden,
 *    ob R�ckgabe und Zieladresse vom gleichen Datentypen sind
 */
public class CallBuilder {

	public static CallNode buildCall(Parser p) {
		String name = p.curToken.getValue();
		p.advance();
		ParameterBuilder.buildParameters(p);

		Logger.log("Funktionsaufruf " + name + " wurde erkannt");

		return new CallNode(name);
	}
}