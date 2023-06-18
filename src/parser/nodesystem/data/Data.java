package parser.nodesystem.data;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.floatingpoint.F32;
import parser.nodesystem.data.numerical.floatingpoint.F64;
import parser.nodesystem.data.numerical.integer.I08;
import parser.nodesystem.data.numerical.integer.I16;
import parser.nodesystem.data.numerical.integer.I32;
import parser.nodesystem.data.numerical.integer.I64;

/**Storage for all datatypes.
 * Is used to receive and send data between Nodes*/
public abstract class Data {

    public final DataType type;

    public Data(DataType type) {
        this.type = type;
    }

    //TODO? factory notwendig?
    public static Data New(DataType dataType) {
        return switch (dataType) {
        //Integer
        case BYTE -> new I08();
        case SHORT -> new I16();
        case INT -> new I32();
        case LONG -> new I64();
        //Floating-Point
        case FLOAT -> new F32();
        case DOUBLE -> new F64();
        case VOID -> new VoidPtr();
        //Error
        default -> throw new Error("Cant instantiate " + dataType);
        };
    }

    public abstract void setValue(Data data);

    public abstract DataType getBaseType();

    public abstract Data getBaseData();

    public abstract boolean isPointer();

    public abstract boolean isObject();

    public abstract boolean isPrimitive();
}