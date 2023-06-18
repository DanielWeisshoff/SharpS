package parser.nodesystem.data.numerical.integer;

import parser.nodesystem.DataType;
import parser.nodesystem.data.numerical.Numerical;

public class Bool extends Numerical {

    public Bool() {
        this(false);
    }

    public Bool(boolean value) {
        super((value == true ? 1 : 0), DataType.BOOLEAN);
    }

}
