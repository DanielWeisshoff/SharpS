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

        p.eat(TokenType.KW_FOR);
        p.eat(TokenType.O_ROUND_BRACKET);

        p.scopeIn("for-init", true);
        VarInitNode in = VarInitialization.parse(p);

        p.eat(TokenType.COMMA);
        ConditionNode cn = Bool.parse(p);

        p.eat(TokenType.COMMA);
        AssignNode an = VarDefinition.parse(p);

        p.eat(TokenType.C_ROUND_BRACKET);
        p.eat(TokenType.COLON);

        ForNode fn = new ForNode();
        fn.init = in;
        fn.condition = cn;
        fn.increment = an;
        fn.block = Block.parse(p, "for-body");

        p.scopeOut(true);
        return fn;
    }
}
