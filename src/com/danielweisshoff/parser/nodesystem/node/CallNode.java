package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/**
 * Calls the defined function
 */
public class CallNode extends Node {
	private final String name;

	public CallNode(String name) {
		super(null, null);
		this.name = name;
	}

	@Override
	public Data<?> execute() {
		Data<?> data = new Data<>(null, DataType.NULL);

		if (BuiltInFunction.builtInFunctions.containsKey(name)) {
			data = BuiltInFunction.builtInFunctions.get(name).call();
			Logger.log("BuiltInFunktion " + name + " wird aufgerufen");
		}

		return data;
	}
}
