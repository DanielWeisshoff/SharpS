package com.danielweisshoff.parser.builders;

import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.Expression;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.nodesystem.node.Node;

import java.util.ArrayList;

public class ExpressionBuilder {

    public static Node buildExpression(Parser p) {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!p.currentToken.isEOF()) {
            if (p.currentToken.isOP()
                    || p.currentToken.isNumeric()
                    || p.currentToken.type() == TokenType.IDENTIFIER) {
                buffer.add(p.currentToken);
                p.advance();
            } else break;
        }
        Token[] arr = new Token[buffer.size()];
        arr = buffer.toArray(arr);
        Node calc = new Expression(arr).toAST();
        if (p.currentToken.type() == TokenType.COMPARISON)
            calc = EquationBuilder.buildEquation(p, calc);

        //Nur temporär
        //Wird auch beim rechten Teil einer gleichung ausgeführt (nicht erwünscht)
        return calc;
    }
}
