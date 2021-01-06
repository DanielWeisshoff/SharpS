package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.nodesystem.BinaryOperator;
import com.danielweisshoff.nodesystem.node.BinaryOperatorNode;
import com.danielweisshoff.nodesystem.node.NumberNode;

import java.util.ArrayList;

public class Calculation {

    private NumberNode result;
    private Token[] tokens;

    public Calculation(Token[] tokens) {
        this.tokens = tokens;
       /* if (tokens.length == 1) {
            result = new NumberNode(Double.parseDouble(tokens[0].getValue()));
        }*/
    }

    public BinaryOperatorNode toAST() {
        ArrayList<Token> buffer = new ArrayList<>();
        ArrayList<BinaryOperator> termOperations = new ArrayList<>();
        ArrayList<BinaryOperatorNode> terms = new ArrayList<>();

        for (Token t : tokens) {
            if (t.isLineOP()) {
                termOperations.add(convertToOperator(t));
                if (buffer.size() == 1)
                    terms.add(createSubTree(buffer));
                buffer.clear();
            } else {
                buffer.add(t);
            }
        }
        if (!buffer.isEmpty()) {
            terms.add(createSubTree(buffer));
            buffer.clear();
        }
        for (BinaryOperatorNode t : terms)
            System.out.println("term: " + t);

        BinaryOperatorNode lastTerm = null;
        for (int i = termOperations.size() - 1; i > 0; i--) {
            BinaryOperatorNode operation;
            if (lastTerm == null) {
                operation = new BinaryOperatorNode(terms.get(i), termOperations.get(i), terms.get(i + 1));
                lastTerm = operation;
            } else {
                operation = new BinaryOperatorNode(terms.get(i), termOperations.get(i), lastTerm);
                lastTerm = operation;
            }
        }
        return lastTerm;
    }

    private BinaryOperatorNode createSubTree(ArrayList<Token> buffer) {
        BinaryOperatorNode lastNode = null;
        for (int i = 0; i < buffer.size(); i++) {
            if (buffer.get(i).isDotOP()) {
                BinaryOperator op = convertToOperator(buffer.get(i));
                NumberNode right = new NumberNode(Double.parseDouble(buffer.get(i + 1).getValue()));
                if (lastNode == null) {
                    NumberNode left = new NumberNode(Double.parseDouble(buffer.get(i - 1).getValue()));
                    BinaryOperatorNode operation = new BinaryOperatorNode(left, op, right);
                    lastNode = operation;
                } else {
                    BinaryOperatorNode operation = new BinaryOperatorNode(lastNode, op, right);
                    lastNode = operation;
                }
            }
        }
        System.out.println("created term: " + lastNode);
        return lastNode;
    }

    private BinaryOperator convertToOperator(Token t) {
        switch (t.type()) {
            case ADD:
                return BinaryOperator.ADD;
            case SUB:
                return BinaryOperator.SUB;
            case MUL:
                return BinaryOperator.MUL;
            case DIV:
                return BinaryOperator.DIV;
            default:
                return null;
        }
    }
}
