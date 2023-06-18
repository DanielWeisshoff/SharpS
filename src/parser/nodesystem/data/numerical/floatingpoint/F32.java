package parser.nodesystem.data.numerical.floatingpoint;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class F32 extends Numerical {

    public F32() {
        this(0f);
    }

    public F32(float value) {
        super(value, DataType.FLOAT);
    }
}
