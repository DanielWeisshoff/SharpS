package parser.parser;

import lexer.TokenType;
import parser.nodesystem.node.binaryoperations.NumberNode;
import parser.nodesystem.node.data.var.DeclareNode;
import parser.nodesystem.node.data.var.DefineNode;
import parser.nodesystem.node.data.var.variable.VarInitNode;
import parser.parser.arithmetic.Expression;

public class VarInitialization {

    /**DECLARATION = EXPR*/
    public static VarInitNode parse(Parser p) {
        DeclareNode declare = VarDeclaration.parse(p);
        p.eat(TokenType.EQUAL);

        NumberNode expr = Expression.parse(p);

        DefineNode define = new DefineNode(declare.name, expr);
        VarInitNode in = new VarInitNode(declare, define);

        return in;
    }
}
