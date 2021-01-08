package com.danielweisshoff.parser;

import java.util.ArrayList;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.nodesystem.node.EntryNode;

public class Parser {

    private EntryNode root;
    private int position = -1;
    private final ArrayList<Token> tokens;
    private Token t;


    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public EntryNode parse() {
        root = new EntryNode();
        enhance();
        while (!t.isEOF()) {
            if (t.isNumeric())
                buildCalculation();
            else
                enhance();
        }
        return root;
    }

    private void enhance() {
        position++;
        t = tokens.get(position);
    }

    //Right now the parser is also the interpreter xD
    private void buildCalculation() {
        ArrayList<Token> buffer = new ArrayList<>();
        boolean lastTokenWasOp = false;
        boolean lastTokenWasEquals = false;
        boolean isEquation = false;
        int firstEqualsPosition = -1;
        while (t.isNumeric() && !lastTokenWasOp || t.isOP() || t.type() == TokenType.EQUALS) {
            if (lastTokenWasEquals)
                isEquation = true;
            if (t.type() == TokenType.EQUALS) {
                firstEqualsPosition = position;
                lastTokenWasEquals = true;
            } else
                lastTokenWasEquals = false;
            lastTokenWasOp = t.isNumeric();
            buffer.add(t);
            enhance();
        }
        Token[] tokenArray = new Token[buffer.size()];
        tokenArray = buffer.toArray(tokenArray);

        if (isEquation) {
            Equation e = new Equation(tokenArray, firstEqualsPosition - 1);
            root.add(e.toAST());

        } else {
            Calculation calculation = new Calculation(tokenArray);
            root.add(calculation.toAST());
        }
    }
}