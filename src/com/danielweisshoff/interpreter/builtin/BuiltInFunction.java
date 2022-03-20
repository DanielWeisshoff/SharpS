package com.danielweisshoff.interpreter.builtin;

import com.danielweisshoff.interpreter.builtin.functions.exitFunction;
import com.danielweisshoff.interpreter.builtin.functions.helpFunction;
import com.danielweisshoff.interpreter.builtin.functions.printFunction;
import com.danielweisshoff.parser.nodesystem.Data;

import java.util.HashMap;

public abstract class BuiltInFunction {

	public static HashMap<String, BuiltInFunction> builtInFunctions = new HashMap<>();

	public static void registerAll() {
		builtInFunctions.put("print", new printFunction());
		builtInFunctions.put("help", new helpFunction());
		builtInFunctions.put("exit", new exitFunction());
	}

	public abstract Data<?> call();
}
