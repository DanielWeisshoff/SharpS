package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;

public class ErrorNode extends Node {

	/**
	 * A placeholder for debugging
	 */
	public ErrorNode() {
		super(new DataType[] {}, DataType.INT, NodeType.ERROR_NODE);
	}

	@Override
	public Data<?> execute() {
		Logger.log("ERRORNODE gefunden! Es gab einen Fehler beim Parsen");
		return new Data<>();
	}

}
