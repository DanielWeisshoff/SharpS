package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.lexer.Token;

/* TODO
 * - RECHNEN MIT KLAMMERN
 * - Rechnen mit unary soll funktionieren
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
        System.out.println(root.execute());

        //Optimierung

        //In Interpreter-Nodes umwandeln
        return root.toNode();
    }
}