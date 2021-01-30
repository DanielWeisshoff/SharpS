package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.container.Class;
import com.danielweisshoff.parser.container.Function;
import com.danielweisshoff.parser.container.Program;
import com.danielweisshoff.parser.container.Variable;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.EntryNode;
import com.danielweisshoff.parser.nodesystem.node.EquationNode;
import com.danielweisshoff.parser.nodesystem.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*TODO
 * - Entries können auch Namen haben
 * - Einen Weg finden, Methoden mit gleichen Namen aber unterschiedlichen Parametern zu speichern
 * - Dictionary<String,MethodGroup>
 +
 */
public class Parser {

    private final Token[] tokens;
    private int position = -1;
    private Token currentToken;

    //Erstmals sind alle Variablen u Methoden global
    public static HashMap<String, Data<?>> variables = new HashMap<>();
    //Später in MethodGroup umändern
    public static HashMap<String, Function> methods = new HashMap<>();

    private final List<Class> classes = new ArrayList<>();
    private final List<EntryNode> entries = new ArrayList<>();

    private Class currentClass = null;
    private Node currentFunction = null;

    public Parser(Token[] tokens) {
        this.tokens = tokens;
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

    private void nextLine() {
        while (currentToken.type() != TokenType.EOF && currentToken.type() != TokenType.NEWLINE) {
            advance();
        }
        advance();
    }

    public Program parse() {

        while (!currentToken.isEOF()) {
            if (currentToken.type() == TokenType.TAB) {
                switch (currentToken.getValue()) {
                    case "1" -> validateAttributeLane();
                    case "2" -> validateMethodLane();
                    default -> validateScopeLane();
                }
            } else {
                validateClassLane();
            }
        }

        Class[] classArray = new Class[classes.size()];
        classArray = classes.toArray(classArray);
        Program program = new Program(classArray);
        return program;
    }

    public void validateClassLane() {
        //Class, Enum, Interface, Struct
        switch (currentToken.getValue()) {
            case "cls":
                Class c = buildClass();
                classes.add(c);
                currentClass = c;
                break;
            default:
                new Error("Class-lane falsch");
        }
    }

    public void validateAttributeLane() {
        advance();
        if (currentToken.type() == TokenType.KEYWORD) {
            advance();
            Variable v = initializeVariable();
        } else
            assignVariable();
    }

    public void validateMethodLane() {
        advance();
        currentFunction = buildFunction();
    }

    public void validateScopeLane() {
        advance();
        if (currentToken.type() == TokenType.KEYWORD) {
            advance();
            initializeVariable();
        } else
            assignVariable();
    }

    /* BUILD
     *
     */
    private Class buildClass() {
        advance();
        if (currentToken.type() != TokenType.IDENTIFIER)
            new Error("Klassenname fehlt");
        String className = currentToken.getValue();

        if (!compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON)) {
            new Error("Methodenstruktur falsch");
        }

        nextLine();

        System.out.println("Klasse " + className + " erkannt");
        return new Class(className);
    }

    private EntryNode buildFunction() {
        boolean isEntry = false;
        boolean isConstructor = false;
        String functionName;
        if (currentToken.getValue().equals("ntr")) {
            isEntry = true;
            functionName = "entry";
        } else if (currentToken.getValue().equals("con")) {
            functionName = "constructor";
            isConstructor = true;
        } else {
            advance();
            functionName = currentToken.getValue();
        }
        if (!compareNextTokens(TokenType.O_ROUND_BRACKET, TokenType.C_ROUND_BRACKET, TokenType.COLON))
            new Error("Falsches Format");

        advance();

        EntryNode functionRoot = new EntryNode(functionName);
        if (isEntry) {
            currentClass.addEntry(functionRoot);
            System.out.println("Entry '" + functionName + "' erkannt");
        } else {
            System.out.println("Funktion '" + functionName + "' erkannt ");
        }
        //Eintragen einer normalen Funktion
        if(!isEntry && !isConstructor){
            Function f = new Function(functionRoot);
            methods.put(functionName,f);
        }

        nextLine();
        return functionRoot;
    }

    private Node buildEquation(Node leftExpression) {
        Token compareType = currentToken;
        advance();
        Node rightExpression = buildExpression();
        System.out.println("Gleichung erstellt");
        return new EquationNode(leftExpression, compareType.getValue(), rightExpression);
    }

    private Variable initializeVariable() {
        String varName = "";
        if (currentToken.type() == TokenType.IDENTIFIER)
            varName = currentToken.getValue();
        else
            new Error("Fehler beim Initialisieren einer Variable");

        advance();
        Variable v;
        if (currentToken.type() == TokenType.ASSIGN) {
            advance();
            Node expr = buildExpression();
            Data<?> result = expr.execute();
            v = new Variable(varName, result);
            System.out.println("Variable initialisiert");
        } else {
            v = new Variable(varName, new Data<>(0, DataType.DOUBLE));
            System.out.println("Variable deklariert");
        }
        nextLine();
        return v;
    }

    private void assignVariable() {
        if (next().type() != TokenType.ASSIGN) {
            buildExpression();
            return;
        }
        String varName = currentToken.getValue();
        if (!variables.containsKey(varName))
            new Error("Variable existiert nicht");

        advance();
        advance();
        Node expr = buildExpression();
        Data<?> result = expr.execute();
        variables.put(varName, result);
        System.out.println("Variablenwert verändert");
        nextLine();
    }

    private Node buildExpression() {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!currentToken.isEOF()) {
            if (currentToken.isOP()
                    || currentToken.isNumeric()
                    || currentToken.type() == TokenType.IDENTIFIER) {
                buffer.add(currentToken);
                advance();
            } else break;
        }
        Token[] arr = new Token[buffer.size()];
        arr = buffer.toArray(arr);
        Node calc = new Expression(arr).toAST();
        if (currentToken.type() == TokenType.COMPARISON)
            calc = buildEquation(calc);

        //Nur temporär
        //Wird auch beim rechten Teil einer gleichung ausgeführt (nicht erwünscht)
        return calc;
    }

    private boolean compareNextTokens(TokenType... type) {
        for (TokenType t : type) {
            advance();
            if (currentToken.type() != t)
                return false;
        }
        return true;
    }
}