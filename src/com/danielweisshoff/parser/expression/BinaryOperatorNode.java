package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.nodesystem.BinaryOperator;
import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.lexer.Token;

/*TODO
 * - execute kann rausgenommen werden, da die Klasse nur zum aufbauen des AST'S ist
 */

/**
 * Calculates the result of the two given Nodes.
 * Is able to use the four basic operators
 */
class BinaryOperatorNode extends ExpressionNode {

    private ExpressionNode leftNode;
    private BinaryOperator operator;
    private ExpressionNode rightNode;
    private double result;

    public BinaryOperatorNode(Token[] tokens) {
        toAST(tokens);
    }

    @Override
    public double execute() {
        calculateResult();
        return result;
    }

    public Node toNode() {
        return new com.danielweisshoff.interpreter.nodesystem.node.BinaryOperatorNode(leftNode.toNode(), operator, rightNode.toNode());
    }

    private void toAST(Token[] tokens) {
        int firstDotOP = 0;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].isDotOP()) {
                firstDotOP = i;
            } else if (tokens[i].isLineOP()) {
                split(tokens, i);
                return;
            }
        }
        split(tokens, firstDotOP);
    }

    private void split(Token[] tokens, int pos) {
        operator = toBinaryOperator(tokens[pos]);

        Token[] left = new Token[pos];
        for (int j = 0; j < pos; j++)
            left[j] = tokens[j];

        Token[] right = new Token[tokens.length - left.length - 1];
        for (int k = pos + 1; k < tokens.length; k++)
            right[k - pos - 1] = tokens[k];

        if (left.length == 1) {
            if (left[0].isNumeric()) {

                double value = Double.parseDouble(left[0].getValue());
                leftNode = new NumberNode(value);
            } else {
                leftNode = new VariableNode(left[0].getValue());
            }
        } else
            leftNode = new BinaryOperatorNode(left);

        if (right.length == 1) {
            if (right[0].isNumeric()) {
                double value = Double.parseDouble(right[0].getValue());
                rightNode = new NumberNode(value);
            } else {
                rightNode = new VariableNode(right[0].getValue());
            }
        } else
            rightNode = new BinaryOperatorNode(right);
    }

    private void calculateResult() {
        double left = leftNode.execute();
        double right = rightNode.execute();
        switch (operator) {
            case ADD -> result = left + right;
            case SUB -> result = left - right;
            case MUL -> result = left * right;
            case DIV -> result = left / right;
        }
    }

    private BinaryOperator toBinaryOperator(Token t) {
        return switch (t.type()) {
            case ADD -> BinaryOperator.ADD;
            case SUB -> BinaryOperator.SUB;
            case MUL -> BinaryOperator.MUL;
            case DIV -> BinaryOperator.DIV;
            default -> null;
        };
    }
}