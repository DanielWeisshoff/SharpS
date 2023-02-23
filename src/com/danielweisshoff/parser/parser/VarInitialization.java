package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.DefineNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.variable.VarInitNode;
import com.danielweisshoff.parser.parser.arithmetic.Expression;

public class VarInitialization {

    public static VarInitNode parse(Parser p) {
        // DECLARATION = EXPR
        DeclareNode declare = VarDeclaration.parse(p);
        p.assume(TokenType.EQUAL, "init '=' missing");

        NumberNode expr = Expression.parse(p);

        DefineNode define = new DefineNode(declare.name, expr);
        VarInitNode in = new VarInitNode(declare, define);

        //p.addInstruction(in);
        return in;
    }
}
