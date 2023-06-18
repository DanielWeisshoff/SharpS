package parser.nodesystem.data.numerical;

import parser.nodesystem.DataType;
import parser.nodesystem.data.Data;

public abstract class Numerical extends Data {

    public Number value;

    public Numerical(Number value, DataType type) {
        super(type);
        this.value = value;
    }

    @Override
    public Data getBaseData() {
        return this;
    }

    @Override
    public DataType getBaseType() {
        return type;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public boolean isPointer() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return true;
    }

    @Override
    public void setValue(Data data) {
        //todo
    }
}
