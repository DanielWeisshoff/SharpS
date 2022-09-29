package com.danielweisshoff.parser.parser;

import com.danielweisshoff.interpreter.builtin.functions.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;
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

    //For block building
    //public Stack<BlockNode> currentBlock = new Stack<>();
    private int scopeDepth = -1;

    //variables
    public SymbolTableManager stm = new SymbolTableManager();

    public Parser() {
        //TODO belongs to semantics
        BuiltInFunction.registerAll();
    }

    public Node parse(Token[] tokens) {
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
     * Check, if current token type equals r, if not print error
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

    // private Token[] tokensToEnd() {
    //     Token[] toks = new Token[tokens.length - position];
    //     int counter = 0;
    //     for (int i = position; i < tokens.length; i++)
    //         toks[counter++] = tokens[i];

    //     return toks;
    // }

    public void scopeIn(BlockNode bn, String scope) {
        stm.newScope(scope);
        scopeDepth++;
        //currentBlock.add(bn);
    }

    public void scopeOut() {
        stm.endScope();
        scopeDepth--;
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

    public int getScope() {
        return scopeDepth;
    }

    public BlockNode getAST() {
        return root;
    }

    // //returns the latest IfNode in the present scope
    // //that doesnt have an else-block
    // IfNode findIfWithoutElseBlock(BlockNode n) {
    //     //get the latest IfNode from current scope
    //     IfNode in = null;
    //     for (int i = n.children.size() - 1; i >= 0; i--)
    //         if (n.children.get(i).nodeType == NodeType.IF_NODE) {
    //             in = (IfNode) n.children.get(i);
    //             break;
    //         }
    //     if (in == null)
    //         new UnimplementedError("else statement doesnt have corresponding if", curToken);

    //     return in.elseBlock == null ? in : findIfWithoutElseBlock(in.elseBlock);
    // }

    // public void addInstruction(Node instruction) {
    //     scopeNode.peek().add(instruction);

    //     if (debug) {
    //         String nodeName = instruction.getClass().getSimpleName();
    //         String tableName = stm.getCurrentTable().getName();
    //         Logger.log(nodeName + " added to scope " + tableName);
    //     }
    // }

}