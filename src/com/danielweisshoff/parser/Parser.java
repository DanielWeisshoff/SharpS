package com.danielweisshoff.parser;

import com.danielweisshoff.interpreter.builtin.BuiltInFunction;
import com.danielweisshoff.interpreter.builtin.BuiltInVariable;
import com.danielweisshoff.interpreter.nodesystem.Data;
import com.danielweisshoff.interpreter.nodesystem.node.EntryNode;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.builders.CallBuilder;
import com.danielweisshoff.parser.builders.ClassBuilder;
import com.danielweisshoff.parser.builders.FunctionBuilder;
import com.danielweisshoff.parser.builders.VariableBuilder;
import com.danielweisshoff.parser.container.Class;
import com.danielweisshoff.parser.container.Function;
import com.danielweisshoff.parser.container.Program;

import java.util.ArrayList;
import java.util.HashMap;

/*TODO
 * - Einen Weg finden, Methoden mit gleichen Namen aber unterschiedlichen Parametern zu speichern
 * - Dictionary<String,MethodGroup>
 * - Alle Variablen werden schon bevor der Interpreter das Programm ausführt gespeichert. Stattdessen sollte
 *    an der Stelle eine AssignNode eingetragen werden, da lokale Variablen während Laufzeit erstellt werden müssen
 */

/**
 * Converts tokens to a runnable AST
 */
public class Parser {

    //Erstmals sind alle Variablen u Methoden global
    public static HashMap<String, Data<?>> variables = new HashMap<>();
    //Später in MethodGroup umändern
    public static HashMap<String, Function> methods = new HashMap<>();

    private final Token[] tokens;
    private final ArrayList<Class> classes = new ArrayList<>();

    public Token currentToken;
    public Class currentClass = null;
    public EntryNode currentFunction = null;
    private int position = -1;

    public Parser(Token[] tokens) {
        BuiltInFunction.registerAll();
        BuiltInVariable.registerAll();

        this.tokens = tokens;
        advance();
    }

    public Program parse() {
        while (!currentToken.isEOF()) {
            if (is(TokenType.TAB)) {
                switch (currentToken.getValue()) {
                    case "1" -> {
                        advance();
                        validateAttributeLane();
                    }
                    case "2" -> {
                        advance();
                        validateMethodLane();
                    }
                    default -> validateScope();
                }
            } else
                validateClassLane();
        }
        Class[] classArray = new Class[classes.size()];
        classes.toArray(classArray);
        return new Program(classArray);
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

    public boolean next(TokenType t) {
        if (position < tokens.length - 1)
            return tokens[position + 1].type() == t;
        return false;
    }

    public void nextLine() {
        while (!is(TokenType.EOF)) {
            if (is(TokenType.NEWLINE) && !next(TokenType.NEWLINE))
                break;
            else
                advance();
        }
        advance();
    }

    /**
     * Vergleicht den aktuellen Token
     */
    public boolean is(TokenType type) {
        return currentToken.type() == type;
    }

    public boolean is(String value) {
        return currentToken.getValue().equals(value);
    }

    /*
     *===================================================================
     *    VALIDATION   VALIDATION  VALIDATION   VALIDATION VALIDATION
     *===================================================================
     */

    public void validateClassLane() {
        //Class, Enum, Interface, Struct
        switch (currentToken.getValue()) {
            case "cls" -> {
                Class c = ClassBuilder.buildClass(this);
                classes.add(c);
                currentClass = c;
                nextLine();
            }
            default -> nextLine();
        }
    }

    public void validateAttributeLane() {
        if (is(TokenType.KEYWORD)) {
            advance();
            currentFunction.add(VariableBuilder.initializeVariable(this));
        } else
            new PError("Class Lane: Falsche Syntax");
        nextLine();
    }

    public void validateMethodLane() {
        if (is(TokenType.KEYWORD))
            currentFunction = FunctionBuilder.build(this);
        nextLine();
    }

    /* TODO
     *  - Ich schätze das wird später in einen eigenen Builder gepackt
     */
    public void validateScope() {
        int tabs = Integer.parseInt(currentToken.getValue());
        advance();

        if (is(TokenType.KEYWORD)) {
            advance();
            currentFunction.add(VariableBuilder.initializeVariable(this));
        } else if (is(TokenType.IDENTIFIER) && next(TokenType.ASSIGN))
            currentFunction.add(VariableBuilder.assignVariable(this));
        else if (is(TokenType.IDENTIFIER))
            currentFunction.add(CallBuilder.buildCall(this));
        nextLine();
    }
}