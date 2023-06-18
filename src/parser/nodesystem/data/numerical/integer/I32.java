package parser.nodesystem.data.numerical.integer;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class I32 extends Numerical {

    public I32() {
        this(0);
    }

    public I32(int value) {
        super(value, DataType.INT);
    }
}
