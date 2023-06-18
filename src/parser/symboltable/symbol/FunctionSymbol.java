package parser.symboltable.symbol;

import java.util.List;

import parser.nodesystem.data.Data;

public class FunctionSymbol extends Symbol {
    private List<Data> argumentTypes;

    public FunctionSymbol(String name, List<Data> argumentTypes, Data returnType) {
        super(name, returnType);
        this.argumentTypes = argumentTypes;
    }

    public List<Data> getArgumentTypes() {
        return this.argumentTypes;
    }
}