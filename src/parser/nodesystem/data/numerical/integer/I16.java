package parser.nodesystem.data.numerical.integer;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class I16 extends Numerical {

    public I16() {
        this((short) 0);
    }

    public I16(short value) {
        super(value, DataType.SHORT);
    }
}
