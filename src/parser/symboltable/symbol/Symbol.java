package parser.symboltable.symbol;

import parser.nodesystem.data.Data;

public abstract class Symbol {
    public String name;
    public Data returnType;

    public Symbol(String name, Data returnType) {
        this.name = name;
        this.returnType = returnType;
    }
}