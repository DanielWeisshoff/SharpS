package parser.parser;

import lexer.Token;
import lexer.TokenType;
import parser.PError.UnimplementedError;
import parser.nodesystem.DataType;
import parser.nodesystem.node.diverse.FunctionNode;
import parser.symboltable.SymbolTable;

/** Eats token by token to spit out an AST. This is the most
 * similiar thing to Pacman you can find. */
public class Parser {

    public static boolean debug = false;

    private FunctionNode root;
    public FunctionNode currentFunction;

    public Token curToken;
    private int position = -1;

    private Token[] tokens;

    public int curInstructionScope = -1;
    public int curScope = -1;

    //variables
    public SymbolTable stm = new SymbolTable();

    public FunctionNode parse(Token[] tokens) {
        this.tokens = tokens;
        eat();

        root = new FunctionNode("global");
        currentFunction = root;

        root.block = Block.parse(this, "ROOT");

        return root;
    }

    /**Consumes the current Token and moves to the next */
    public void eat() {
        eat(1);
    }

    void eat(int steps) {
        for (int i = 0; i < steps; i++) {
            position++;
            if (position < tokens.length)
                curToken = tokens[position];
            else {
                curToken = new Token(TokenType.EOF, "", -1, -1, -1);
                break;
            }
        }
    }

    public void retreat() {
        retreat(1);
    }

    private void retreat(int times) {
        for (int i = 0; i < times; i++)
            position--;

        if (position > -1)
            curToken = tokens[position];
    }

    public boolean is(String value) {
        return is(value);
    }

    /** Vergleicht den aktuellen Token*/
    public boolean is(TokenType type) {
        return curToken.type == type;
    }

    /** Eats current Token if its type = t*/
    public void eat(TokenType t) {
        eat(t, "Expected symbol '" + t + "'");
    }

    /** Consumes the current Token and moves to the next
     * only if the tokentype is correct, else spits out an error*/
    public void eat(TokenType t, String error) {
        if (is(t))
            eat();
        else
            new UnimplementedError(error, curToken);
    }

    private Token next() {
        return next(1);
    }

    private Token next(int lookahead) {
        if (position < tokens.length - lookahead)
            return tokens[position + lookahead];
        //TODO col and pos weird
        return new Token(TokenType.EOF, "", -1, -1, -1);
    }

    public boolean next(int lookahead, TokenType t) {
        return next(lookahead).type == t;
    }

    public boolean next(TokenType t) {
        return next().type == t;
    }

    //TODO remove param
    public void scopeIn(String scope) {
        scopeIn(scope, false);
    }

    //TODO remove, ScopeNode will do this
    public void scopeIn(String scope, boolean stayInScope) {
        if (!stayInScope)
            curScope++;
        stm.enterScope();
    }

    public void scopeOut() {
        scopeOut(false);
    }

    public void scopeOut(boolean sameScope) {
        stm.exitScope();
        if (!sameScope)
            curScope--;
    }

    //TODO? in Helferklasse auslagern
    public DataType getPrimitiveType(TokenType keyword) {
        DataType type = null;
        switch (keyword) {
        case KW_BYTE -> type = DataType.BYTE;
        case KW_SHORT -> type = DataType.SHORT;
        case KW_INT -> type = DataType.INT;
        case KW_LONG -> type = DataType.LONG;
        case KW_FLOAT -> type = DataType.FLOAT;
        case KW_DOUBLE -> type = DataType.DOUBLE;
        default -> new UnimplementedError("parser: unknown primitive type " + keyword, curToken);
        }
        return type;
    }

    //TODO wird in Block u. Instruction overused
    /**
     * Begin Of Instruction
     * parser will advance() to the next instruction
     */
    public void BOI() {
        while (is(TokenType.COMMENT) || is(TokenType.NEWLINE)
                || (is(TokenType.TAB) && (next(TokenType.COMMENT) && next(TokenType.NEWLINE)))) {
            eat();
        }
        //TODO? ???
        if (is(TokenType.TAB))
            curInstructionScope = Integer.parseInt(curToken.value);
        else
            curInstructionScope = 0;
    }

    public FunctionNode getAST() {
        return root;
    }

    public SymbolTable getSymbolTable() {
        return stm;
    }
}