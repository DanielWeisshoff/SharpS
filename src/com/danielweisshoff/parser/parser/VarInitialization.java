package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.VarInitNode;
import com.danielweisshoff.parser.parser.arithmetic.Expression;

public class VarInitialization {

    public static VarInitNode parse(Parser p) {
        // DECLARATION = EXPR
        DeclareNode dn = VarDeclaration.parse(p);

        p.assume(TokenType.EQUAL, "init '=' missing");

        NumberNode expr = Expression.parse(p);
        p.stm.findVariable(dn.getName()).node = expr;

        VarInitNode in = new VarInitNode(dn.getName(), dn.dataType, expr);

        //p.addInstruction(in);
        return in;
    }
}
