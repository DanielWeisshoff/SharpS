package com.danielweisshoff.parser.parser.pointer;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.node.data.var.pointer.PointerNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.parser.arithmetic.Expression;

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
