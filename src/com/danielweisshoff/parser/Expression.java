package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.nodesystem.BinaryOperator;
import com.danielweisshoff.nodesystem.node.BinaryOperatorNode;
import com.danielweisshoff.nodesystem.node.Node;
import com.danielweisshoff.nodesystem.node.NumberNode;
import com.danielweisshoff.nodesystem.node.VariableNode;

import java.util.ArrayList;

public class Expression {

    private final Token[] tokens;

    public Expression(Token[] tokens) {
        this.tokens = tokens;
    }

    /**
     * Kompliziertere Methode, welche kein Lookahead benötigt und stattdessen
     * unverarbeitete Token buffert.
     * Das Prinzip ist gut, der Code könnte besser sein
     *
     * @return The root Node of the calculation
     */
    public Node toAST() {

        if (tokens.length == 1)
            if (tokens[0].isNumeric())
                return new NumberNode(Double.parseDouble(tokens[0].getValue()));
            else
                return new VariableNode(tokens[0].getValue());


        ArrayList<Token> buffer = new ArrayList<>();
        ArrayList<BinaryOperator> termOperators = new ArrayList<>();
        ArrayList<Node> terms = new ArrayList<>();

        for (Token t : tokens) {
            if (t.isLineOP()) {
                termOperators.add(convertToOperator(t));
                terms.add(createSubTree(buffer));
                buffer.clear();
            } else
                buffer.add(t);
        }
        //Term, der rechts vom letzten operator verbleibt hinzufügen
        if (!buffer.isEmpty()) {
            terms.add(createSubTree(buffer));
        }
        if (termOperators.isEmpty()) {
            return terms.get(0);
        }

        BinaryOperatorNode lastTerm = null;
        for (int i = 0; i < termOperators.size(); i++) {
            if (lastTerm == null)
                lastTerm = new BinaryOperatorNode(terms.get(i), termOperators.get(i), terms.get(i + 1));
            else
                lastTerm = new BinaryOperatorNode(lastTerm, termOperators.get(i), terms.get(i + 1));
        }
        return lastTerm;
    }

    private Node createSubTree(ArrayList<Token> buffer) {
        if (buffer.size() == 1)
            if (buffer.get(0).isNumeric())
                return new NumberNode(Double.parseDouble(buffer.get(0).getValue()));
            else
                return new VariableNode(buffer.get(0).getValue());
        BinaryOperatorNode lastNode = null;
        for (int i = 0; i < buffer.size(); i++) {
            if (buffer.get(i).isDotOP()) {
                BinaryOperator op = convertToOperator(buffer.get(i));
                Node right;
                if (buffer.get(i + 1).isNumeric())
                    right = new NumberNode(Double.parseDouble(buffer.get(i + 1).getValue()));
                else
                    right = new VariableNode(buffer.get(i + 1).getValue());
                BinaryOperatorNode operation;
                if (lastNode == null) {
                    Node left;
                    if (buffer.get(i - 1).isNumeric())
                        left = new NumberNode(Double.parseDouble(buffer.get(i - 1).getValue()));
                    else
                        left = new VariableNode(buffer.get(i - 1).getValue());
                    operation = new BinaryOperatorNode(left, op, right);
                } else
                    operation = new BinaryOperatorNode(lastNode, op, right);
                lastNode = operation;
            }
        }
        return lastNode;
    }

    private BinaryOperator convertToOperator(Token t) {
        return switch (t.type()) {
            case ADD -> BinaryOperator.ADD;
            case SUB -> BinaryOperator.SUB;
            case MUL -> BinaryOperator.MUL;
            case DIV -> BinaryOperator.DIV;
            default -> null;
        };
    }
}