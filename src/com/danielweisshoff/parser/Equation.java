package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.nodesystem.node.EquationNode;
import com.danielweisshoff.nodesystem.node.Node;

public class Equation {

    private final Token[] tokens;
    private final int splitPosition;

    public Equation(Token[] tokens, int splitPosition) {
        this.tokens = tokens;
        this.splitPosition = splitPosition;
    }

    public EquationNode toAST() {
        // Splitposition wird immer ein Operator sein
        int rightEquationTokenAmount = tokens.length - splitPosition - 2;
        // linken u rechten Term füllen
        Token[] leftEquationTokens = new Token[splitPosition];
        Token[] rightEquationTokens = new Token[rightEquationTokenAmount];

        for (int i = 0; i < tokens.length; i++) {
            if (i != splitPosition && i != splitPosition + 1) {
                if (i < splitPosition)
                    leftEquationTokens[i] = tokens[i];
                else
                    rightEquationTokens[i - 2 - splitPosition] = tokens[i];
            }
        }
        Node leftEquation = new Calculation(leftEquationTokens).toAST();
        Node rightEquation = new Calculation(rightEquationTokens).toAST();
        return new EquationNode(leftEquation, rightEquation);
    }
}
