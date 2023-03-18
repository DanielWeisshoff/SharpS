package parser.nodesystem;

/* TODO
 *  - asChar and asBoolean conversion are cursed
 *  - idk if asPointer and asLong should be separate or not
 *  - Maybe Pointer will be a class ?!?
 */

/**Storage for all datatypes.
 * Is used to receive and send data between Nodes*/
public class Data {
    public final DataType dataType;
    private final Number[] value;

    public Data() {
        this(DataType.INT);
    }

    public Data(DataType dataType) {
        this(dataType, 1);
    }

    //handelt sich um Array
    public Data(DataType dataType, int size) {
        this.dataType = dataType;

        value = new Number[size];
        for (int i = 0; i < size; i++) {
            setValue(0, i);
        }
    }

    //handelt sich um einzelnes Feld
    public Data(Number value, DataType dataType) {
        this.value = new Number[1];
        this.value[0] = value;
        this.dataType = dataType;
    }

    public void setValue(Number value) {
        setValue(value, 0);
    }

    public void setValue(Number value, int index) {
        Number n = null;
        switch (dataType) {
        //integer 
        case BYTE -> n = value.byteValue();
        case SHORT -> n = value.shortValue();
        case INT -> n = value.intValue();
        case LONG -> n = value.longValue();
        //floating point 
        case FLOAT -> n = value.floatValue();
        case DOUBLE -> n = value.doubleValue();
        //diverse
        case BOOLEAN -> n = value.byteValue() != 0 ? 1 : 0;
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
        }
        this.value[index] = n;
    }

    public boolean isArray() {
        return value.length > 1;
    }

    /*
     * Integers
     */
    public byte asByte() {
        return asByte(0);
    }

    public byte asByte(int index) {
        return value[index].byteValue();
    }

    public short asShort() {
        return asShort(0);
    }

    public short asShort(int index) {
        return value[index].shortValue();
    }

    public int asInt() {
        return asInt(0);
    }

    public int asInt(int index) {
        return value[index].intValue();
    }

    public long asLong() {
        return asLong(0);
    }

    public long asLong(int index) {
        return value[index].longValue();
    }

    /*
     * Floating Point
     */
    public float asFloat() {
        return asFloat(0);
    }

    public float asFloat(int index) {
        return value[index].floatValue();
    }

    public double asDouble() {
        return asDouble(0);
    }

    public double asDouble(int index) {
        return value[index].doubleValue();
    }

    /*
     * Diverse
     */
    //TODO char als short? was ist mit wchar?
    public char asChar() {
        return asChar(0);
    }

    public char asChar(int index) {
        return (char) value[index].shortValue();
    }

    public boolean asBoolean() {
        return asBoolean(0);
    }

    public boolean asBoolean(int index) {
        return value[index].byteValue() != 0;
    }

    public long asPointer() {
        return asPointer(0);
    }

    public long asPointer(int index) {
        return value[index].longValue();
    }
}