package interpreter.builtin.functions;

import parser.nodesystem.Data;
import parser.nodesystem.DataType;

public class printFunction extends BuiltInFunction {

    @Override
    public Data call() {
        System.out.println(">>Print Funktion");
        return new Data(1, DataType.INT);
    }
}
