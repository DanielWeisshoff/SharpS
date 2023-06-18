package parser.nodesystem.data;

import parser.nodesystem.DataType;

public class VoidPtr extends Data{

    public VoidPtr() {
        super(DataType.VOID);
    }

    @Override
    public void setValue(Data data) {
    
    }

    @Override
    public DataType getBaseType() {
        return DataType.VOID;
    }

    @Override
    public Data getBaseData() {
        return null;
    }

    @Override
    public boolean isPointer() {
        return false;
    }

    @Override
    public boolean isObject() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }
    
}
