package com.danielweisshoff.interpreter.builtin.functions;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.parser.nodesystem.Data;

public class exitFunction extends BuiltInFunction {

	//TODO int parameter fuer errorcode
	@Override
	public Data<?> call() {

		System.exit(0);
		return null;
	}

}