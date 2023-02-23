package parser.parser.loops;

import lexer.TokenType;
import parser.nodesystem.node.BlockNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;
import parser.nodesystem.node.loops.WhileNode;
import parser.parser.Block;
import parser.parser.Bool;
import parser.parser.Parser;

public class While {

    public static WhileNode parse(Parser p) {
        // while ( BOOL ) : BLOCK
        p.assume(TokenType.KW_WHILE, "Keyword WHILE missing");
        p.assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

        ConditionNode cn = Bool.parse(p);

        p.assume(TokenType.C_ROUND_BRACKET, "Missing close bracket for while-loop");
        p.assume(TokenType.COLON, "while-body not defined");

        BlockNode bn = Block.parse(p, "while-body");

        WhileNode wn = new WhileNode(cn);
        wn.whileBlock = bn;

        return wn;
    }
}
