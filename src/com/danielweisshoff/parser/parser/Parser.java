package com.danielweisshoff.parser.parser;

import com.danielweisshoff.interpreter.builtin.functions.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.symboltable.SymbolTableManager;

// TODO durch for-init wird falsch gescoped -> siehe
//      symboltable struktur

//TODO addInstruction schlecht gelöst
/**
 * Converts tokens to a runnable AST
 */
public class Parser {

    public static boolean debug = true;

    private BlockNode root;

    public Token curToken;
    private int position = -1;

    private Token[] tokens;

    public int curInstructionScope = -1;
    public int curScope = -1;

    //variables
    public SymbolTableManager stm = new SymbolTableManager();

    public Parser() {
        //TODO belongs to semantics
        BuiltInFunction.registerAll();
    }

    public BlockNode parse(Token[] tokens) {
        this.tokens = tokens;
        advance();

        root = Block.parse(this, "ROOT");
        return root;
    }

    public void advance() {
        advance(1);
    }

    void advance(int steps) {
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

    /**
     * Vergleicht den aktuellen Token
     */
    public boolean is(TokenType type) {
        return curToken.type() == type;
    }

    public boolean is(String value) {
        return is(value);
    }

    /**
     * Compares the current tokens type
     */
    public void assume(TokenType t, String error) {
        if (is(t))
            advance();
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
        return next(lookahead).type() == t;
    }

    public boolean next(TokenType t) {
        return next().type() == t;
    }

    public void scopeIn(String scope) {
        scopeIn(scope, false);
    }

    public void scopeIn(String name, boolean sameScope) {
        if (!sameScope)
            curScope++;
        stm.newScope(name, curScope);
    }

    public void scopeOut() {
        scopeOut(false);
    }

    public void scopeOut(boolean sameScope) {
        stm.endScope();
        if (!sameScope)
            curScope--;
    }

    public void printSymbolTable() {
        stm.print();
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
                || (is(TokenType.TAB) && next(TokenType.COMMENT) || (is(TokenType.TAB) && next(TokenType.NEWLINE)))) {
            advance();
        }

        if (is(TokenType.TAB))
            curInstructionScope = Integer.parseInt(curToken.value);
        else
            curInstructionScope = 0;
    }

    public BlockNode getAST() {
        return root;
    }
}