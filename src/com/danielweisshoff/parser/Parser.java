package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;
import com.danielweisshoff.nodesystem.node.EntryNode;
import com.danielweisshoff.nodesystem.node.EquationNode;
import com.danielweisshoff.nodesystem.node.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    private final Token[] tokens;
    private int position = -1;
    private final boolean error = false;
    private Token currentToken;
    //Nur zum testen
    public static HashMap<String, Data<?>> variables = new HashMap<>();
    private EntryNode rootNode;

    public Parser(Token[] tokens) {
        this.tokens = tokens;
        rootNode = new EntryNode();
        advance();
    }

    private void advance() {
        position++;
        if (position < tokens.length) {
            currentToken = tokens[position];
        }
    }

    private Token next() {
        if (position < tokens.length - 1)
            return tokens[position + 1];
        return new Token(TokenType.EOF, null);
    }

    public EntryNode parse() {
        rootNode = findConstructor();
        if (rootNode == null)
            return null;
        while (!currentToken.isEOF() && !error) {
            if (currentToken.isNumeric())
                buildExpression();
            else if (currentToken.type() == TokenType.KEYWORD)
                evaluateKeyword();
            else if (currentToken.type() == TokenType.IDENTIFIER)
                assignVariable();
            else
                advance();
        }
        return rootNode;
    }

    private EntryNode findConstructor() {
        EntryNode conNode = null;
        while (!currentToken.isEOF() && !error) {
            if (currentToken.type() == TokenType.KEYWORD) {
                if (currentToken.getValue().equals("con")) {
                    conNode = buildConstructor();
                    if (conNode != null)
                        return conNode;
                } else
                    advance();
            }
        }
        return null;
    }

    private EntryNode buildConstructor() {
        advance();
        if (currentToken.type() != TokenType.O_ROUND_BRACKET)
            return null;
        advance();
        if (currentToken.type() != TokenType.C_ROUND_BRACKET)
            return null;
        advance();
        if (currentToken.type() != TokenType.COLON)
            return null;
        advance();
        return new EntryNode();
    }

    private Node buildExpression() {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!currentToken.isEOF()) {
            if (currentToken.isOP() || currentToken.isNumeric() || currentToken.type() == TokenType.IDENTIFIER) {
                buffer.add(currentToken);
                advance();
            } else break;
        }
        Token[] arr = new Token[buffer.size()];
        arr = buffer.toArray(arr);
        Node calc = new Expression(arr).toAST();
        if (currentToken.type() == TokenType.COMPARISON) {
            calc = buildEquation(calc);
        }
        //Nur temporär
        //Wird auch beim rechten Teil einer gleichung ausgeführt (nicht erwünscht)
        rootNode.add(calc);
        return calc;
    }

    private Node buildEquation(Node leftExpression) {
        Token compareType = currentToken;
        advance();
        Node rightExpression = buildExpression();
        return new EquationNode(leftExpression, compareType.getValue(), rightExpression);
    }

    private void evaluateKeyword() {
        switch (currentToken.getValue()) {
            case "int" -> initializeVariable();
            case "con" -> buildConstructor();
        }
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
            buildExpression();
            return;
        }
        String varName = currentToken.getValue();
        if (!variables.containsKey(varName)) {
            System.out.println("Variable existiert nicht");
            return;
        }
        advance();
        advance();
        Node expr = buildExpression();
        Data<?> result = expr.execute();
        variables.put(varName, result);
    }
}