package parser.nodesystem.data.numerical.integer;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class I08 extends Numerical {

    public I08() {
        this((byte) 0);
    }

    public I08(byte value) {
        super(value, DataType.BYTE);
    }
}