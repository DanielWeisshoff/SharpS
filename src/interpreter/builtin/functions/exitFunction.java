package interpreter.builtin.functions;

import parser.nodesystem.Data;

public class exitFunction extends BuiltInFunction {

    //TODO int parameter fuer errorcode
    @Override
    public Data call() {

        System.exit(0);
        return null;
    }

}
