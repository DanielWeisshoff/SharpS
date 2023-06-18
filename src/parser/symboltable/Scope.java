package parser.symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.symboltable.symbol.Symbol;

//TODO right now, a var and a fnc cant have the same name
public class Scope {
    private final Map<String, Symbol> symbols = new HashMap<>();
    public final Scope parent;
    public final List<Scope> children = new ArrayList<>();

    public Scope(Scope parent) {
        this.parent = parent;
        if (parent != null)
            parent.addChild(this);
    }

    public Symbol resolve(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol != null)
            return symbol;
        else if (parent != null)
            return parent.resolve(name);
        else
            return null;
    }

    public void define(Symbol symbol) {
        symbols.put(symbol.name, symbol);
    }

    public void addChild(Scope child) {
        children.add(child);
    }

    public void removeChild(Scope child) {
        children.remove(child);
    }
}
