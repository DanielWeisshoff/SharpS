package com.danielweisshoff.parser;

import java.util.ArrayList;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.nodesystem.node.BinaryOperatorNode;
import com.danielweisshoff.nodesystem.node.EquationNode;

public class Parser {

    public void parse(ArrayList<Token> tokens) {

        if (tokens.get(0).isNumeric())
            onNumberInput(tokens);
        if (tokens.size() >= 2) {
            if (tokens.get(0).isOP()
                    && tokens.get(1).isNumeric())
                onNumberInput(tokens);
        }
    }

    //Right now the parser is also the interpreter xD
    private void onNumberInput(ArrayList<Token> tokens) {
        // EOF entfernen
        tokens.remove(tokens.size() - 1);


        // passende Operatoren in Vorzeichen umwandeln
        Token[] tokenArray = signNumbers(tokens);

        // falls == enthalten ist, gleichung checken
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokenArray[i].type() == TokenType.EQUALS && tokenArray[i + 1].type() == TokenType.EQUALS) {
                Equation e = new Equation(tokens, i);
                e.toAST().execute().print();
                return;
            }
        }
        // Ansonsten normale Rechnung ausführen
        Calculation calculation = new Calculation(
                tokenArray);
        BinaryOperatorNode rootCalculation = calculation.toAST();
        rootCalculation.execute().print();
        // System.out.println(calculation.getResult());
    }

    // Wandelt passende Operatoren in Vorzeichen um
    private Token[] signNumbers(ArrayList<Token> tokens) {
        // Erste Zahl ganz links validieren
        if (tokens.size() >= 2) {

            if (tokens.get(1).isNumeric() && tokens.get(0).type() == TokenType.SUB) {
                tokens.get(1).setValue("-" + tokens.get(1).getValue());
                tokens.remove(0);
            }
            for (int i = 1; i < tokens.size(); i++) {
                if (tokens.get(i).isNumeric()
                        && tokens.get(i - 1).type() == TokenType.SUB
                        && tokens.get(i - 2).isOP()) {
                    tokens.get(i).setValue(
                            "-" + tokens.get(i).getValue());
                    tokens.remove(i - 1);
                }
            }
        }
        Token[] tokenArray = new Token[tokens
                .size()];
        for (int i = 0; i < tokens.size(); i++)
            tokenArray[i] = tokens.get(i);

        return tokenArray;
    }
}