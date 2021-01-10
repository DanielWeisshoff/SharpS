package com.danielweisshoff.parser;

import java.util.ArrayList;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
<<<<<<< Updated upstream

public class Parser {

    public void parse(ArrayList<Token> tokens) {
=======
import com.danielweisshoff.nodesystem.Data;
import com.danielweisshoff.nodesystem.DataType;
import com.danielweisshoff.nodesystem.node.EntryNode;
import com.danielweisshoff.nodesystem.node.EquationNode;
import com.danielweisshoff.nodesystem.node.Node;

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
>>>>>>> Stashed changes

        if (tokens.get(0).isNumeric())
            doCalculatingStuff(tokens);
        if (tokens.size() >= 2) {
            if (tokens.get(0).isOP()
                    && tokens.get(1).isNumeric())
                doCalculatingStuff(tokens);
        }
    }

    private void doCalculatingStuff(
            ArrayList<Token> tokens) {

<<<<<<< Updated upstream
        // EOF entfernen
        tokens.remove(tokens.size() - 1);

        // Gültige Operatoren als Vorzeichen in Nummertoken
        // einfügen
        Token[] tokenArray = signNumbers(tokens);

        // falls == enthalten ist, gleichung checken
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokenArray[i].type() == TokenType.EQUALS
                    && tokenArray[i + 1]
                    .type() == TokenType.EQUALS) {
                createEquation(tokens, i);
                return;
            }
        }
        // Ansonsten normale Rechnung ausführen
        Calculation calculation = new Calculation(
                tokenArray);
        System.out.println(calculation.getResult());
=======
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
>>>>>>> Stashed changes
    }

    // Wandelt Zahlen je nach vorzeichen um
    private Token[] signNumbers(ArrayList<Token> tokens) {
        // Erste Zahl ganz links validieren
        if (tokens.size() >= 2) {

            if (tokens.get(1).isNumeric()
                    && tokens.get(0)
                    .type() == TokenType.SUB) {
                tokens.get(1)
                        .setValue(
                                "-" + tokens
                                        .get(1)
                                        .getValue());
                tokens.remove(0);
            }

<<<<<<< Updated upstream
            for (int i = 1; i < tokens.size(); i++) {
                if (tokens.get(i).isNumeric()
                        &&
                        tokens.get(i - 1)
                                .type() == TokenType.SUB
                        &&
                        tokens.get(i - 2).isOP()) {
                    tokens.get(i)
                            .setValue(
                                    "-" + tokens
                                            .get(i)
                                            .getValue());
                    tokens.remove(i - 1);
                }
            }
        }

        Token[] tokenArray = new Token[tokens
                .size()];
        for (int i = 0; i < tokens.size(); i++) {
            tokenArray[i] = tokens.get(i);
        }
        return tokenArray;
    }

    private void createEquation(ArrayList<Token> tokens,
                                int splitPosition) {

        // Splitposition wird immer ein Operator sein

        int leftEquationTokenAmount = splitPosition;
        int rightEquationTokenAmount = tokens.size()
                - splitPosition - 2;
        // linken u rechten Term füllen
        Token[] leftEquationTokens = new Token[leftEquationTokenAmount];
        Token[] rightEquationTokens = new Token[rightEquationTokenAmount];

        for (int i = 0; i < tokens.size(); i++) {
            if (i == splitPosition
                    || i == splitPosition + 1)
                continue;
            else if (i < splitPosition)
                leftEquationTokens[i] = tokens.get(i);
            else {
                rightEquationTokens[i - 2
                        - splitPosition] = tokens.get(i);
            }
=======
        while (!currentToken.isEOF())
            if (currentToken.isOP() || currentToken.isNumeric() || currentToken.type() == TokenType.IDENTIFIER) {
                buffer.add(currentToken);
                advance();
            } else break;

        Token[] tokenArray = new Token[buffer.size()];
        tokenArray = buffer.toArray(tokenArray);
        Node calculation = new Expression(tokenArray).toAST();
        if (currentToken.type() == TokenType.COMPARISON) {
            calculation = buildEquation(calculation);
        }
        return calculation;
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
>>>>>>> Stashed changes
        }

        Calculation leftEquation = new Calculation(
                leftEquationTokens);
        Calculation rightEquation = new Calculation(
                rightEquationTokens);
        compareEquations(leftEquation, rightEquation);
    }

<<<<<<< Updated upstream
    private void compareEquations(Calculation a,
                                  Calculation b) {
        if (a.getResult() == b.getResult())
            System.out.println("TRUE");
        else
            System.out.println("FALSE");
=======
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

    private boolean checkNextTokens(TokenType... type) {
        for (TokenType t : type) {
            advance();
            if (currentToken.type() != t)
                return false;
        }
        return true;
>>>>>>> Stashed changes
    }
}