package com.danielweisshoff.parser;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.builders.CallBuilder;
import com.danielweisshoff.parser.builders.ClassBuilder;
import com.danielweisshoff.parser.builders.FunctionBuilder;
import com.danielweisshoff.parser.builders.VariableBuilder;
import com.danielweisshoff.parser.container.Class;
import com.danielweisshoff.parser.container.Function;
import com.danielweisshoff.parser.container.Program;
import com.danielweisshoff.parser.container.Variable;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.CallNode;
import com.danielweisshoff.parser.nodesystem.node.EntryNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*TODO
 * - Entries können auch Namen haben
 * - Einen Weg finden, Methoden mit gleichen Namen aber unterschiedlichen Parametern zu speichern
 * - Dictionary<String,MethodGroup>
 */
public class Parser {

    //Erstmals sind alle Variablen u Methoden global
    public static HashMap<String, Data<?>> variables = new HashMap<>();
    //Später in MethodGroup umändern
    public static HashMap<String, Function> methods = new HashMap<>();

    private final Token[] tokens;
    private final List<Class> classes = new ArrayList<>();
    private final List<EntryNode> entries = new ArrayList<>();

    public Token currentToken;
    public Class currentClass = null;
    public EntryNode currentFunction = null;
    private int position = -1;

    public Parser(Token[] tokens) {
        BuiltInFunction.registerAll();
        this.tokens = tokens;
        advance();
    }

    public void advance() {
        position++;
        if (position < tokens.length)
            currentToken = tokens[position];
    }

    public Token next() {
        if (position < tokens.length - 1)
            return tokens[position + 1];
        return new Token(TokenType.EOF, "");
    }

    public void nextLine() {
        while (currentToken.type() != TokenType.EOF) {
            if (currentToken.type() == TokenType.NEWLINE && next().type() != TokenType.NEWLINE) {
                break;
            } else
                advance();
        }
        advance();
    }

    public Program parse() {

        while (!currentToken.isEOF()) {
            if (currentToken.type() == TokenType.TAB && next().type() == TokenType.NEWLINE)
                nextLine();
            else if (currentToken.type() == TokenType.TAB) {
                switch (currentToken.getValue()) {
                    case "1" -> {
                        advance();
                        validateAttributeLane();
                    }
                    case "2" -> {
                        advance();
                        validateMethodLane();
                    }
                    default -> {
                        advance();
                        validateScopeLane();
                    }
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

    public boolean compareNextTokens(TokenType... type) {
        for (TokenType t : type) {
            advance();
            if (currentToken.type() != t) {
                advance();
                return false;
            }
        }
        advance();
        return true;
    }

    public void validateClassLane() {
        //Class, Enum, Interface, Struct
        switch (currentToken.getValue()) {
            case "cls":
                Class c = ClassBuilder.buildClass(this);
                classes.add(c);
                currentClass = c;
                nextLine();
                break;
            default:
                new Error("Class-lane falsch " + currentToken.getDescription());
        }
    }

    public void validateAttributeLane() {
        if (currentToken.type() == TokenType.KEYWORD) {
            advance();
            Variable v = VariableBuilder.initializeVariable(this);
            variables.put(v.getName(), v.getData());
        } else
            VariableBuilder.assignVariable(this);
        nextLine();
    }

    public void validateMethodLane() {
        currentFunction = FunctionBuilder.buildFunction(this);
        nextLine();
    }

    public void validateScopeLane() {
        if (currentToken.type() == TokenType.KEYWORD) {
            advance();
            Variable v = VariableBuilder.initializeVariable(this);
            variables.put(v.getName(), v.getData());
            nextLine();
        } else if (currentToken.type() == TokenType.IDENTIFIER && next().type() == TokenType.ASSIGN) {
            VariableBuilder.assignVariable(this);
            nextLine();
        } else if (currentToken.type() == TokenType.IDENTIFIER) {
            CallNode n = CallBuilder.buildCall(this);
            currentFunction.add(n);
            nextLine();
        } else {
            //Fehlende Syntax erstmal überspringen
            nextLine();
        }
    }
}