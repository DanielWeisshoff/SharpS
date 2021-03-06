package com.danielweisshoff.parser.expression;

import com.danielweisshoff.interpreter.nodesystem.node.*;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;

/*TODO
 * - execute kann rausgenommen werden, da die Klasse nur zum aufbauen des AST'S ist
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
        return switch (operator) {
            case ADD -> new BinaryAddNode(leftNode.toNode(), rightNode.toNode());
            case SUB -> new BinarySubNode(leftNode.toNode(), rightNode.toNode());
            case MUL -> new BinaryMulNode(leftNode.toNode(), rightNode.toNode());
            case DIV -> new BinaryDivNode(leftNode.toNode(), rightNode.toNode());
        };
    }

    private void toAST(Token[] tokens) {
        int inClamp = 0;
        int firstDotOP = -1;

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].type() == TokenType.O_ROUND_BRACKET)
                inClamp++;
            else if (tokens[i].type() == TokenType.C_ROUND_BRACKET)
                inClamp--;
            else if (tokens[i].isDotOP() && inClamp == 0)
                firstDotOP = i;
            else if (tokens[i].isLineOP() && inClamp == 0) {
                split(tokens, i);
                return;
            }
        }
        if (firstDotOP != -1)
            split(tokens, firstDotOP);
        else if (tokens.length > 1)
            //Wenn Operator gefunden wurde, ist die gesamte Rechnung eingeklammert
            unclamp(tokens);
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
            } else
                leftNode = new VariableNode(left[0].getValue());
        } else
            leftNode = new BinaryOperatorNode(left);

        if (right.length == 1) {
            if (right[0].isNumeric()) {
                double value = Double.parseDouble(right[0].getValue());
                rightNode = new NumberNode(value);
            } else
                rightNode = new VariableNode(right[0].getValue());
        } else
            rightNode = new BinaryOperatorNode(right);
    }

    private void unclamp(Token[] tokens) {
        Token[] newTokens = new Token[tokens.length - 2];
        for (int i = 1; i < tokens.length - 1; i++)
            newTokens[i - 1] = tokens[i];
        toAST(newTokens);
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