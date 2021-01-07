package com.danielweisshoff.parser;

import java.util.ArrayList;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.nodesystem.node.EntryNode;
import com.danielweisshoff.nodesystem.node.Node;

public class Parser {

    private EntryNode root;
    private int position = -1;
    private final ArrayList<Token> tokens;
    private Token t;


    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public EntryNode parse() {
        root = new EntryNode();
        enhance();
        while (!t.isEOF()) {
            //try {
            if (t.isNumeric())
                buildCalculation();
            else
                enhance();
            // } catch (Exception pickachu) {
            //   System.out.println("Fehler beim parsen: " + pickachu);
            // }
        }
        System.out.println("---EOF---");
        return root;
    }

    private void enhance() {
        position++;
        t = tokens.get(position);
    }

    //Right now the parser is also the interpreter xD
    private void buildCalculation() {
        ArrayList<Token> buffer = new ArrayList<>();
        boolean lastTokenWasOp = false;
        boolean lastTokenWasEquals = false;
        boolean isEquation = false;
        int firstEqualsPosition = -1;
        while (t.isNumeric() && !lastTokenWasOp || t.isOP() || t.type() == TokenType.EQUALS) {
            if (lastTokenWasEquals)
                isEquation = true;
            if (t.type() == TokenType.EQUALS) {
                firstEqualsPosition = position;
                lastTokenWasEquals = true;
            } else
                lastTokenWasEquals = false;
            lastTokenWasOp = t.isNumeric();
            buffer.add(t);
            enhance();
        }
        // passende Operatoren in Vorzeichen umwandeln
        Token[] tokenArray = signNumbers(buffer);

        if (isEquation) {
            Equation e = new Equation(buffer, firstEqualsPosition - 1);
            root.add(e.toAST());

        } else {
            Calculation calculation = new Calculation(tokenArray);
            root.add(calculation.toAST());
        }
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
        Token[] tokenArray = new Token[tokens.size()];
        for (int i = 0; i < tokens.size(); i++)
            tokenArray[i] = tokens.get(i);

        return tokenArray;
    }
}