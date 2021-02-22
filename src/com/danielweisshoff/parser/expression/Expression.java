package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.lexer.Token;

/* TODO
 *  - RECHNEN MIT KLAMMERN
 */

/**
 * Builds an expression
 */
public class Expression {

    private final Token[] tokens;

    public Expression(Token[] tokens) {
        this.tokens = tokens;
    }

    public Node toAST() {
        var root = new BinaryOperatorNode(tokens);
        root.execute();

        //Hier Optimierung

        //In Interpreter-Nodes umwandeln
        return root.toNode();
    }
}