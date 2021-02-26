package com.danielweisshoff.parser.builders;

import com.danielweisshoff.interpreter.nodesystem.node.Node;
import com.danielweisshoff.interpreter.nodesystem.node.NumberNode;
import com.danielweisshoff.interpreter.nodesystem.node.VariableNode;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.Parser;
import com.danielweisshoff.parser.expression.Expression;

import java.util.ArrayList;

/* TODO
 *  - Bei einer Equation wird rekusiv aufgerufen -> bessere Lösung finden
 */
public class ExpressionBuilder {

    public static Node buildExpression(Parser p) {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!p.currentToken.isEOF() && !p.is(TokenType.NEWLINE)) {
            if (p.currentToken.isOP()
                    || p.currentToken.isNumeric()
                    || p.is(TokenType.IDENTIFIER)
                    || p.is(TokenType.O_ROUND_BRACKET)
                    || p.is(TokenType.C_ROUND_BRACKET)) {
                buffer.add(p.currentToken);
                p.advance();
            } else break;
        }
        Token[] arr = convertUnarys(buffer);

        Node calculation;
        if (arr.length == 1)
            if (arr[0].isNumeric())
                calculation = new NumberNode(Double.parseDouble(arr[0].getValue()));
            else
                calculation = new VariableNode(arr[0].getValue());
        else
            calculation = new Expression(arr).toAST();


        if (p.is(TokenType.COMPARISON))
            calculation = EquationBuilder.buildEquation(p, calculation);
        else
            Logger.log("Rechnung erstellt");

        return calculation;
    }

    private static Token[] convertUnarys(ArrayList<Token> tokens) {
        if (tokens.get(0).type() == TokenType.SUB) {
            tokens.get(1).setValue("-" + tokens.get(1).getValue());
            tokens.remove(0);
        }

        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).isOP() && tokens.get(i + 1).type() == TokenType.SUB) {
                tokens.get(i + 2).setValue("-" + tokens.get(i + 2).getValue());
                tokens.remove(i + 1);
            }
        }

        Token[] arr = new Token[tokens.size()];
        return tokens.toArray(arr);
    }
}
