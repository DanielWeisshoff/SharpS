package parser.nodesystem.data;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.integer.I32;

public class Array extends Data {

    public final Data[] array;
    public final int size;
    public final DataType dataType;

    public Array(DataType dataType, int size) {
        super(DataType.ARRAY);
        this.dataType = dataType;
        this.size = size;

        array = new Data[size];
    }

    public Array(DataType dataType, Data... array) {
        super(DataType.ARRAY);
        this.dataType = dataType;
        this.size = array.length;
        this.array = array;
    }

    @Override
    public void setValue(Data data) {
        setData(data, new I32(0));

    }

    public void setData(Data d, Object index) {
        int i = (int) ((I32) index).value;
        array[i] = d;
    }

    //TODO array[0] could be null
    @Override
    public DataType getBaseType() {
        return array[0].getBaseType();
    }

    //TODO idk
    @Override
    public boolean isPointer() {
        return false;
    }

    //TODO idk
    @Override
    public boolean isObject() {
        return false;
    }

    //TODO idk
    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public Data getBaseData() {
        throw new Error("getBaseData bei Array nicht eindeutig");
    }

    public Data getBaseData(Data index) {

        if (index.getBaseType() != DataType.INT)
            throw new Error("Integer needed for array index.");

        int i = (int) ((I32) index).value;

        if (i >= size)
            throw new Error("Index out of bounds for array");

        return array[i].getBaseData();
    }
}
