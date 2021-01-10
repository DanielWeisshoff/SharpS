package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;
import com.danielweisshoff.nodesystem.node.EntryNode;
import com.danielweisshoff.nodesystem.node.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    private final EntryNode root;
    private int position = -1;
    private final ArrayList<Token> tokens;
    private Token currentToken;
    //Nur zum testen
    public static HashMap<String, Data<?>> variables = new HashMap<>();
    private boolean error = false;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        root = new EntryNode();
        advance();
    }

    private void advance() {
        position++;
        if (position < tokens.size()) {
            currentToken = tokens.get(position);
        }
    }

    private Token next() {
        if (position < tokens.size() - 1)
            return tokens.get(position + 1);
        return new Token(TokenType.EOF, null);
    }

    private void quit() {
        error = true;
    }


    public EntryNode parse() {
        while (!currentToken.isEOF() && !error) {
            if (currentToken.isNumeric())
                buildExpression();
            if (currentToken.type() == TokenType.KEYWORD)
                initializeVariable();
            else if (currentToken.type() == TokenType.IDENTIFIER)
                assignVariable();
            else
                advance();
        }
        return root;
    }

    private Node buildExpression() {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!currentToken.isEOF()) {
            if (currentToken.isOP() || currentToken.isNumeric()) {
                buffer.add(currentToken);
                advance();
            } else break;
        }
        Token[] arr = new Token[buffer.size()];
        arr = buffer.toArray(arr);
        Node calc = new Expression(arr).toAST();
        root.add(calc);
        return calc;
    }

    private void initializeVariable() {
        advance();
        String varName = "";
        if (currentToken.type() == TokenType.IDENTIFIER)
            varName = currentToken.getValue();
        else {
            System.out.println("Falsches Format");
            return;
        }
        advance();
        if (currentToken.type() != TokenType.ASSIGN) {
            variables.put(varName, new Data<>(0, DataType.DOUBLE));
            System.out.println("variable erstellt");
        } else {
            advance();
            Node expr = buildExpression();
            Data<?> result = expr.execute();
            variables.put(varName, result);
        }
    }

    private void assignVariable() {
        if (next().type() != TokenType.ASSIGN) {
            printVariable();
            return;
        }
        String varName = currentToken.getValue();
        if (!variables.containsKey(varName)) {
            System.out.println("Variable existiert nicht");
            quit();
            return;
        }
        advance();
        advance();
        Node expr = buildExpression();
        Data<?> result = expr.execute();
        variables.put(varName, result);
    }

    //Nur zum testen
    private void printVariable() {
        String varName = currentToken.getValue();
        if (!variables.containsKey(varName)) {
            System.out.println("Variable existiert nicht");
            quit();
            return;
        }
        Data<?> var = variables.get(varName);
        System.out.println(var.toDouble());
        advance();
    }
}