package com.danielweisshoff.parser;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.nodesystem.node.EquationNode;
import com.danielweisshoff.nodesystem.node.Node;

import java.util.ArrayList;

public class Equation {

    private final ArrayList<Token> tokens;
    private final int splitPosition;

    public Equation(ArrayList<Token> tokens, int splitPosition) {
        this.tokens = tokens;
        this.splitPosition = splitPosition;
    }

    public EquationNode toAST() {
        // Splitposition wird immer ein Operator sein
        int rightEquationTokenAmount = tokens.size() - splitPosition - 2;
        // linken u rechten Term füllen
        Token[] leftEquationTokens = new Token[splitPosition];
        Token[] rightEquationTokens = new Token[rightEquationTokenAmount];

        for (int i = 0; i < tokens.size(); i++) {
            if (i != splitPosition && i != splitPosition + 1) {
                if (i < splitPosition)
                    leftEquationTokens[i] = tokens.get(i);
                else
                    rightEquationTokens[i - 2 - splitPosition] = tokens.get(i);
            }
        }
        Node leftEquation = new Calculation(leftEquationTokens).toAST();
        Node rightEquation = new Calculation(rightEquationTokens).toAST();
        return new EquationNode(leftEquation, rightEquation);
    }
}
