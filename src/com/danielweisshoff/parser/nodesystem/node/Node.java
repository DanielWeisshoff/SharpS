package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

/**
 * The power of creation lies at our feet.
 * The only thing holding you back is <i>imagination</i>
 */
public abstract class Node {

	/*//Spezifiziert, welche/r Datentyp angenommen / ausgegeben wird
	private final DataType[] inputType;
	private final DataType outputType;*/

	public NodeType nodeType;

	public Node(DataType[] inputType, DataType outputType, NodeType nodeType) {
		// this.inputType = inputType;
		// this.outputType = outputType;
		this.nodeType = nodeType;
	}

	public abstract Data run();
}
