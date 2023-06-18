package parser.nodesystem.data.numerical.integer;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class I64 extends Numerical {

    public I64() {
        this(0);
    }

    public I64(long value) {
        super(value, DataType.LONG);
    }
}
