package com.danielweisshoff.parser.nodesystem.node.data;
// package com.danielweisshoff.interpreter.nodesystem.node.data;

// import com.danielweisshoff.interpreter.builtin.BuiltInVariable;
// import com.danielweisshoff.interpreter.nodesystem.Data;
// import com.danielweisshoff.interpreter.nodesystem.DataType;
// import com.danielweisshoff.interpreter.nodesystem.node.Node;
// import com.danielweisshoff.parser.Parser;

// /* TODO
//  *   - Sollte eventuell ID anstatt Name speichern
//  */

// /**
//  * Holds the name of a variable and returns the stored data
//  */
// public class VariableNode extends Node {

// 	private final String name;

// 	public VariableNode(String name) {
// 		super(null, null);
// 		this.name = name;
// 	}

// 	@Override
// 	public Data<?> execute() {
// 		Data<?> data = new Data<>(null, DataType.NULL);
// 		if (BuiltInVariable.builtInVariables.containsKey(name)) {
// 			data = BuiltInVariable.builtInVariables.get(name).getData();
// 		}
// 		return data;
// 	}
// }
