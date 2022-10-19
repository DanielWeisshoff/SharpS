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
 */
//TODO? to many ctors? Could bring alot of bugs later
//TODO ctors without datatype, so the existing type will be used instead (should be standard)
public class Data {

    public final DataType dataType;
    public Number value;

    public Data(DataType dataType) {
        this.dataType = dataType;

        //generate default value 
        switch (dataType) {
        //integer 
        case BYTE -> value = Byte.valueOf((byte) 0);
        case SHORT -> value = Short.valueOf((short) 0);
        case INT -> value = Integer.valueOf(0);
        case LONG -> value = Long.valueOf(0);
        //floating point 
        case FLOAT -> value = Float.valueOf(0);
        case DOUBLE -> value = Double.valueOf(0);
        //diverse
        case CHAR -> {
            System.out.println("err01 not implemented");
            System.exit(0);
        }
        case POINTER -> {
            System.out.println("err02 not implemented");
            System.exit(0);
        }
        case VOID -> {
            System.out.println("err03 not implemented");
            System.exit(0);
        }
        case BOOLEAN -> value = Byte.valueOf((byte) 0);
        }
    }

    public Data() {
        this.value = Integer.valueOf(0);
        this.dataType = DataType.INT;
    }

    public Data(Number data, DataType dataType) {
        this.value = data;
        this.dataType = dataType;
    }

    public void setValue(Number value) {
        //check if Number is same as dataType
        this.value = value;
    }

    public Number getData() {
        return value;
    }

    /*
     * Integers
     */
    public byte asByte() {
        assert value != null;
        return value.byteValue();
    }

    public short asShort() {
        assert value != null;
        return value.shortValue();
    }

    public int asInt() {
        assert value != null;
        return value.intValue();
    }

    public long asLong() {
        assert value != null;
        return value.longValue();
    }

    /*
     * Floating Point
     */
    public float asFloat() {
        assert value != null;
        return value.floatValue();
    }

    public double asDouble() {
        assert value != null;
        return value.doubleValue();
    }

    /*
     * Diverse
     */
    public char asChar() {
        assert value != null;
        return (char) value.shortValue();
    }

    public boolean asBoolean() {
        assert value != null;
        return value.byteValue() == 1;
    }

    public long asPointer() {
        assert value != null;
        return value.longValue();
    }
}
