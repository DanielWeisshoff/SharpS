package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;

public class Calculation {

    private NumberNode result;

    private Calculation leftTerm;
    private Token operator;
    private Calculation rightTerm;

    public Calculation(Token[] calculation) {
        if (calculation.length == 1) {
            result = new NumberNode(Double.parseDouble(calculation[0].getValue()));
        } else {
            int splitPosition = findSplitPosition(
                    calculation);
            splitCalculation(calculation, splitPosition);
        }
    }

    private int findSplitPosition(Token[] calculation) {
        int firstDotOpPosition = -1;
        for (int i = calculation.length - 2; i > 0; i--) {
            if (calculation[i].isDotOP()
                    && firstDotOpPosition == -1) {
                firstDotOpPosition = i;
            } else if (calculation[i].isLineOP()) {
                return i;
            }
        }
        return firstDotOpPosition;
    }

    private void splitCalculation(Token[] calculation,
                                  int splitPosition) {
        // Splitposition ist immer ein Operator
        operator = calculation[splitPosition];

        int leftSideTokenAmount = splitPosition;
        int rightSideTokenAmount = calculation.length
                - splitPosition - 1;

        // linken u rechten Term füllen
        Token[] leftSide = new Token[leftSideTokenAmount];
        Token[] rightSide = new Token[rightSideTokenAmount];

        for (int i = 0; i < calculation.length; i++) {
            if (i == splitPosition)
                continue;
            else if (i < splitPosition)
                leftSide[i] = calculation[i];
            else {
                rightSide[i - splitPosition
                        - 1] = calculation[i];
            }
        }
        leftTerm = new Calculation(leftSide);
        rightTerm = new Calculation(rightSide);
    }

    public double getResult() {
        if (result == null)
            calculateResult();
        return result.getValue();
    }

    private void calculateResult() {
        double result;
        switch (operator.type()) {
            case ADD:
                result = leftTerm.getResult()
                        + rightTerm.getResult();
                break;
            case SUB:
                result = leftTerm.getResult()
                        - rightTerm.getResult();
                break;
            case MUL:
                result = leftTerm.getResult()
                        * rightTerm.getResult();
                break;
            case DIV:
                result = leftTerm.getResult()
                        / rightTerm.getResult();
                break;
            default:
                result = -1337;
        }
        this.result = new NumberNode(result);
    }
}
