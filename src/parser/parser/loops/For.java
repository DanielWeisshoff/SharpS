package parser.parser.loops;

import lexer.TokenType;
import parser.nodesystem.node.data.var.AssignNode;
import parser.nodesystem.node.data.var.variable.VarInitNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;
import parser.nodesystem.node.loops.ForNode;
import parser.parser.Block;
import parser.parser.Bool;
import parser.parser.Parser;
import parser.parser.VarDefinition;
import parser.parser.VarInitialization;

public class For {

    // KW_FOR ( INITIALIZATION , BOOL , DEFINITION ) : BLOCK
    public static ForNode parse(Parser p) {

        p.assume(TokenType.KW_FOR, "Keyword FOR missing");
        p.assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for for-loop");

        p.scopeIn("for-init", true);
        VarInitNode in = VarInitialization.parse(p);

        p.assume(TokenType.COMMA, "comma missing");
        ConditionNode cn = Bool.parse(p);

        p.assume(TokenType.COMMA, "comma missing");
        AssignNode an = VarDefinition.parse(p);

        p.assume(TokenType.C_ROUND_BRACKET, "Missing closed bracket for for-loop");
        p.assume(TokenType.COLON, "for-body-declarator missing");

        ForNode fn = new ForNode();
        fn.init = in;
        fn.condition = cn;
        fn.increment = an;
        fn.block = Block.parse(p, "for-body");

        p.scopeOut(true);
        return fn;
    }
}
