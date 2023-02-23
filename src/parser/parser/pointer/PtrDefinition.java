package parser.parser.pointer;

import lexer.TokenType;
import parser.PError.UnimplementedError;
import parser.nodesystem.node.data.var.pointer.PointerNode;
import parser.parser.Parser;
import parser.parser.arithmetic.Expression;

public class PtrDefinition {

    public static PointerNode parse(Parser p) {
        PointerNode pn = Pointer.parse(p);
        switch (p.curToken.type()) {
        // PTR = ADRESS
        case EQUAL:
            p.advance();
            pn.adress = Adress.parse(p);
            break;
        // PTR + = EXPR
        case PLUS:
            //TODO
            p.assume(TokenType.EQUAL, "pointer def = missing");
            Expression.parse(p);
            break;
        //PTR - = EXPR
        case MINUS:
            //TODO
            p.assume(TokenType.EQUAL, "pointer def = missing");
            Expression.parse(p);
            break;
        default:
            new UnimplementedError("error parsing pointer def", p.curToken);
        }

        // p.addInstruction(pn);
        return pn;
    }
}
