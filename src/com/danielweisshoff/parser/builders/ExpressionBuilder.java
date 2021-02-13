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
 * - Bei einer Equatipn wird rekusiv aufgerufen -> bessere Lösung finden
 */
public class ExpressionBuilder {

    public static Node buildExpression(Parser p) {
        ArrayList<Token> buffer = new ArrayList<>();

        while (!p.currentToken.isEOF() && p.currentToken.type() != TokenType.NEWLINE) {
            if (p.currentToken.isOP()
                    || p.currentToken.isNumeric()
                    || p.currentToken.type() == TokenType.IDENTIFIER) {
                buffer.add(p.currentToken);
                p.advance();
            } else break;
        }
        Token[] arr = new Token[buffer.size()];
        arr = buffer.toArray(arr);

        Node calculation;
        if (arr.length == 1)
            if (arr[0].isNumeric())
                calculation = new NumberNode(Double.parseDouble(arr[0].getValue()));
            else
                calculation = new VariableNode(arr[0].getValue());
        else
            calculation = new Expression(arr).toAST();


        if (p.currentToken.type() == TokenType.COMPARISON)
            calculation = EquationBuilder.buildEquation(p, calculation);
        else
            Logger.log("Rechnung erstellt");

        return calculation;
    }
}
