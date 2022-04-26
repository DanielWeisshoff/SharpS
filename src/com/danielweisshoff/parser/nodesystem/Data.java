package com.danielweisshoff.parser.nodesystem;

import java.security.Permissions;

import com.danielweisshoff.parser.PError;

/* TODO
 *  - asChar and asBoolean conversion are cursed
 *  - idk if asPointer and asLong should be separate or not
 *  - Maybe Pointer will be a class ?!?
 */

/**
 * Storage for all datatypes.
 * Is used to receive and send data between Nodes
 *
 */
//TODO? to many ctors? Could bring alot of bugs later
public class Data {

	public final DataType dataType;
	public Number data;

	public Data(DataType dataType) {
		this.dataType = dataType;

		//generate default value 
		switch (dataType) {
		//integer 
		case BYTE -> data = Byte.valueOf((byte) 0);
		case SHORT -> data = Short.valueOf((short) 0);
		case INT -> data = Integer.valueOf(0);
		case LONG -> data = Long.valueOf(0);
		//floating point 
		case FLOAT -> data = Float.valueOf(0);
		case DOUBLE -> data = Double.valueOf(0);
		//diverse
		case CHAR -> new PError("err01 not implemented");
		case POINTER -> new PError("err02 not implemented");
		case VOID -> new PError("err03 not implemented");
		case BOOLEAN -> data = Byte.valueOf((byte) 0);
		}
	}

	public Data() {
		this.data = Integer.valueOf(0);
		this.dataType = DataType.INT;
	}

	public Data(Number data, DataType dataType) {
		this.data = data;
		this.dataType = dataType;
	}

	public void setValue(Number value) {
		//check if Number is same as dataType
		data = value;
	}

	public Number getData() {
		return data;
	}

	/*
	 * Integers
	 */
	public byte asByte() {
		assert data != null;
		return data.byteValue();
	}

	public short asShort() {
		assert data != null;
		return data.shortValue();
	}

	public int asInt() {
		assert data != null;
		return data.intValue();
	}

	public long asLong() {
		assert data != null;
		return data.longValue();
	}

	/*
	 * Floating Point
	 */
	public float asFloat() {
		assert data != null;
		return data.floatValue();
	}

	public double asDouble() {
		assert data != null;
		return data.doubleValue();
	}

	/*
	 * Diverse
	 */
	public char asChar() {
		assert data != null;
		return (char) data.shortValue();
	}

	public boolean asBoolean() {
		assert data != null;
		return data.byteValue() == 1;
	}

	public long asPointer() {
		assert data != null;
		return data.longValue();
	}
}
