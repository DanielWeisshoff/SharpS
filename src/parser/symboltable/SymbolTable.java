package parser.symboltable;

import parser.symboltable.symbol.Symbol;

public class SymbolTable {

    private final Scope globalScope;
    private Scope currentScope;

    public SymbolTable() {
        globalScope = new Scope(null);
        currentScope = globalScope;
    }

    public void enterScope() {
        currentScope = new Scope(currentScope);
    }

    public void exitScope() {
        if (currentScope != globalScope) {
            Scope parent = currentScope.parent;
            //TODO? needed
            // parent.removeChild(currentScope);
            currentScope = parent;
        }
    }

    public void define(Symbol symbol) {
        currentScope.define(symbol);
    }

    public boolean isDefined(Symbol symbol) {
        return resolve(symbol.name) != null;
    }

    /** Recursive search for the Symbol.
     * @return null if Symbol not defined*/
    public Symbol resolve(String name) {
        return currentScope.resolve(name);
    }

    /**@return null if Symbol not defined */
    public Symbol resolveInGlobal(String name) {
        return globalScope.resolve(name);
    }

    //TODO implementation
    public void print() {

    }
}