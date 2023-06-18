package parser.nodesystem.data.numerical.floatingpoint;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class F64 extends Numerical {

    public F64() {
        this(0f);
    }

    public F64(double value) {
        super(value, DataType.DOUBLE);
    }

}
