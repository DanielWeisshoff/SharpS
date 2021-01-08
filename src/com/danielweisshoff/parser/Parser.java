package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.nodesystem.node.EntryNode;

import java.util.ArrayList;

public class Parser {

    private final EntryNode root;
    private int position = -1;
    private final ArrayList<Token> tokens;
    private Token currentToken;


    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        root = new EntryNode();
        advance();
    }

    private void advance() {
        position++;
        currentToken = tokens.get(position);
    }

    public EntryNode parse() {
        while (!currentToken.isEOF()) {
            if (currentToken.isNumeric())
                buildExpression();
            else
                advance();
        }
        return root;
    }

    private void buildExpression() {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!currentToken.isEOF()) {
            if (currentToken.isOP() || currentToken.isNumeric()) {
                buffer.add(currentToken);
                advance();
            } else break;
        }
        Token[] arr = new Token[buffer.size()];
        arr = buffer.toArray(arr);
        root.add(new Expression(arr).toAST());
    }
}