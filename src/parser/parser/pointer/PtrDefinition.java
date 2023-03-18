package parser.parser.pointer;

import lexer.TokenType;
import parser.PError.UnimplementedError;
import parser.nodesystem.node.data.var.pointer.PointerNode;
import parser.parser.Parser;
import parser.parser.arithmetic.Expression;

public class PtrDefinition {

    public static PointerNode parse(Parser p) {
        PointerNode pn = Pointer.parse(p);
        switch (p.curToken.type) {
        // PTR = ADRESS
        case EQUAL:
            p.eat();
            pn.adress = Adress.parse(p);
            break;
        // PTR + = EXPR
        case PLUS:
            //TODO
            p.eat(TokenType.EQUAL);
            Expression.parse(p);
            break;
        //PTR - = EXPR
        case MINUS:
            //TODO
            p.eat(TokenType.EQUAL);
            Expression.parse(p);
            break;
        default:
            new UnimplementedError("error parsing pointer def", p.curToken);
        }

        return pn;
    }
}
