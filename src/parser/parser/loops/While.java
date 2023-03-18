package parser.parser.loops;

import lexer.TokenType;
import parser.nodesystem.node.diverse.BlockNode;
import parser.nodesystem.node.logic.conditions.ConditionNode;
import parser.nodesystem.node.loops.WhileNode;
import parser.parser.Block;
import parser.parser.Bool;
import parser.parser.Parser;

public class While {

    public static WhileNode parse(Parser p) {
        // while ( BOOL ) : BLOCK
        p.eat(TokenType.KW_WHILE);
        p.eat(TokenType.O_ROUND_BRACKET);

        ConditionNode cn = Bool.parse(p);

        p.eat(TokenType.C_ROUND_BRACKET);
        p.eat(TokenType.COLON);

        BlockNode bn = Block.parse(p, "while-body");

        WhileNode wn = new WhileNode(cn);
        wn.whileBlock = bn;

        return wn;
    }
}
