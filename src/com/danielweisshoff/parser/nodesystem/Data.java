package com.danielweisshoff.parser.nodesystem;

public class Data<T extends Number> {


    private final DataType dataType;
    private final T data;

    public Data() {
        dataType = DataType.NULL;
        data = null;
    }

    public Data(T data, DataType dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    public T getData() {
        return data;
    }

    public void print() {
        System.out.println(data);
    }

    public double toDouble() {
        return data.doubleValue();
    }

    public int toInt() {
        return data.intValue();
    }

    public String toString() {
        return data.toString();
    }

    public byte toByte() {
        return data.byteValue();
    }
}

