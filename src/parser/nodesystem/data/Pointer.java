package parser.nodesystem.data;

import parser.nodesystem.DataType;

// TODO create static Data for nullptr, void, and use it for
// TODO default constructor
public class Pointer extends Data {

    public Data pointsTo;

    public Pointer(Data pointsTo) {
        super(DataType.POINTER);
        this.pointsTo = pointsTo;
    }

    @Override
    public void setValue(Data data) {
        pointsTo = data;
    }

    @Override
    public DataType getBaseType() {
        return pointsTo.getBaseType();
    }

    @Override
    public boolean isPointer() {
        return true;
    }

    //TODO? check
    @Override
    public boolean isObject() {
        return pointsTo.isObject();
    }

    //TODO? check
    @Override
    public boolean isPrimitive() {
        return pointsTo.isPrimitive();
    }

    @Override
    public Data getBaseData() {
        return pointsTo.getBaseData();
    }

}
