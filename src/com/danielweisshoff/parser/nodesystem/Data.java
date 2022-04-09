package com.danielweisshoff.parser.nodesystem;

/* TODO
 *  - asChar and asBoolean conversion are cursed
 *  - idk if asPointer and asLong should be separate or not
 *  - Maybe Pointer will be a class ?!?
 */

/**
 * Storage for all datatypes.
 * Is used to receive and send data between Nodes
 *
 * @param <T> Any datatype
 */
public class Data<T extends Number> {

	public final DataType dataType;
	public T data;

	public Data() {
		dataType = DataType.VOID;
		data = null;
	}

	public Data(DataType dataType) {
		this.dataType = dataType;
	}

	public Data(T data, DataType dataType) {
		this.data = data;
		this.dataType = dataType;
	}

	public T getData() {
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
