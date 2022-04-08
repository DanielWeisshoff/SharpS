package com.danielweisshoff.parser.nodesystem.node.data;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.Node;

/**
 * Stores a normal or floating-point number
 */
public class NumberNode extends Node {

	//TODO bisl weird 
	//? einfach double benutzen?
	private final Data<Double> value;

	public NumberNode(double value) {
		super(new DataType[] { DataType.DOUBLE }, DataType.DOUBLE);
		this.value = new Data<Double>(value, DataType.DOUBLE);
	}

	public Data<?> getData() {
		return value;
	}

	@Override
	public Data<?> execute() {
		return value;
	}
}
