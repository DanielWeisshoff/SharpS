package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;
import com.danielweisshoff.nodesystem.node.EntryNode;
import com.danielweisshoff.nodesystem.node.EquationNode;
import com.danielweisshoff.nodesystem.node.Node;
import com.danielweisshoff.parser.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {

    private final Token[] tokens;
    private int position = -1;
    private Token currentToken;
    //Nur zum testen
    public static HashMap<String, Data<?>> variables = new HashMap<>();
    private EntryNode rootNode;

    private final List<Class> classes = new ArrayList<>();
    private Class currentClass = null;
    private Node currentFunction = null;

    public Parser(Token[] tokens) {
        this.tokens = tokens;
        rootNode = new EntryNode();
        advance();
    }

    private void advance() {
        position++;
        if (position < tokens.length)
            currentToken = tokens[position];
    }

    private Token next() {
        if (position < tokens.length - 1)
            return tokens[position + 1];
        return new Token(TokenType.EOF, "");
    }

    public EntryNode parse() {
        while (!currentToken.isEOF()) {
            if (currentToken.getValue().equals("cls")) {
                advance();
                buildClass();
            } else if (currentClass != null) {
                if (currentToken.type() == TokenType.TAB) {
                    switch (currentToken.getValue()) {
                        //Attribute Lane
                        case "1":
                            advance();
                            if (currentToken.type() == TokenType.KEYWORD) {
                                advance();
                                initializeVariable();
                            } else
                                assignVariable();
                            break;
                        //Function Lane
                        case "2":
                            advance();
                            buildFunction();
                            break;
                    }
                } else advance();
            } else advance();
        }
        return rootNode; //PLatzhalter
    }

    private void buildClass() {
        if (currentToken.type() != TokenType.IDENTIFIER)
            return;
        String className = currentToken.getValue();
        if (!checkNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            return;
        advance();
        System.out.println("Klasse erkannt");

        Class c = new Class(className);
        classes.add(c);
        currentClass = c;
    }

    private void buildFunction() {
        boolean isEntry = false;
        boolean isCon = false;
        String functionName;
        if (currentToken.getValue().equals("ntr")) {
            isEntry = true;
            functionName = "entry";
        } else if (currentToken.getValue().equals("con")) {
            isCon = true;
            functionName = "constructor";
        } else {
            advance();
            functionName = currentToken.getValue();
        }
        if (!checkNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            return;
        advance();

        if (isEntry)
            System.out.println("Entry erkannt");
        else if (isCon)
            System.out.println("Konstruktor erkannt");
        else
            System.out.println("Funktion erkannt ");

        //Platzhalter
        EntryNode functionRoot = new EntryNode();
        currentClass.addMethod(functionName, functionRoot);
    }

    private Node buildEquation(Node leftExpression) {
        Token compareType = currentToken;
        advance();
        Node rightExpression = buildExpression();
        System.out.println("Gleichung erstellt");
        return new EquationNode(leftExpression, compareType.getValue(), rightExpression);
    }

    private void initializeVariable() {
        String varName = "";
        if (currentToken.type() == TokenType.IDENTIFIER)
            varName = currentToken.getValue();
        else
            return;
        advance();
        if (currentToken.type() == TokenType.ASSIGN) {
            advance();
            Node expr = buildExpression();
            Data<?> result = expr.execute();
            variables.put(varName, result);
            System.out.println("Variable initialisiert");
        } else {
            variables.put(varName, new Data<>(0, DataType.DOUBLE));
            System.out.println("Variable deklariert");
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
        System.out.println("Variablenwert veraendert");
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

    private boolean checkNextTokens(TokenType... type) {
        for (TokenType t : type) {
            advance();
            if (currentToken.type() != t)
                return false;
        }
        return true;
    }
}
