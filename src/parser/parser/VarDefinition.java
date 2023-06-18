package parser.parser;

import lexer.TokenType;
import parser.PError.UnimplementedError;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.DefineNode;
import parser.parser.arithmetic.Expression;
import parser.parser.shortcuts.PostDecrement;
import parser.parser.shortcuts.PostIncrement;
import parser.parser.shortcuts.PreDecrement;
import parser.parser.shortcuts.PreIncrement;

public class VarDefinition {

    public static AssignNode parse(Parser p) {
        if (p.is(TokenType.MINUS))
            return PreDecrement.parse(p);
        if (p.is(TokenType.PLUS))
            return PreIncrement.parse(p);

        String varName = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        AssignNode an = null;
        Node expr = null;
        switch (p.curToken.type) {
        // ID = EXPR
        case EQUAL:
            p.eat();
            expr = Expression.parse(p);
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID + =  EXPR || ID + +
        case PLUS:
            if (p.next(TokenType.PLUS)) {
                p.retreat();
                an = PostIncrement.parse(p);
                break;
            }

            p.eat();
            p.eat(TokenType.EQUAL);

            expr = Expression.parse(p);
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID -=  EXPR || ID --
        case MINUS:
            if (p.next(TokenType.MINUS)) {
                p.retreat();
                an = PostDecrement.parse(p);
                break;
            }

            p.eat();
            p.eat(TokenType.EQUAL);

            expr = Expression.parse(p);
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID *=  EXPR
        case STAR:
            p.eat();
            p.eat(TokenType.EQUAL);

            expr = Expression.parse(p);
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID /=  EXPR
        case SLASH:
            p.eat();
            p.eat(TokenType.EQUAL);

            expr = Expression.parse(p);
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;
        // ID %=  EXPR
        case PERCENT:
            p.eat();
            p.eat(TokenType.EQUAL);

            expr = Expression.parse(p);
            an = new DefineNode(varName, expr);
            an.expression = expr;
            break;

        default:
            new UnimplementedError("unknown operation", p.curToken);
        }

        return an;
    }
}
