package com.danielweisshoff.interpreter.builtin.functions;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

public class printFunction extends BuiltInFunction {

	@Override
	public Data<?> call() {
		System.out.println(">>Print Funktion");
		return new Data<>(1, DataType.INT);
	}
}
