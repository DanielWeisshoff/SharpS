package parser.parser.loops;

import lexer.TokenType;
import parser.nodesystem.node.logic.conditions.ConditionNode;
import parser.nodesystem.node.loops.DoWhileNode;
import parser.parser.Block;
import parser.parser.Bool;
import parser.parser.Parser;

public class DoWhile {

    public static DoWhileNode parse(Parser p) {
        // do while ( BOOL ) : BLOCK
        p.eat(TokenType.KW_DO);
        p.eat(TokenType.KW_WHILE);
        p.eat(TokenType.O_ROUND_BRACKET);

        ConditionNode cn = Bool.parse(p);

        p.eat(TokenType.C_ROUND_BRACKET);
        p.eat(TokenType.COLON);

        DoWhileNode dwn = new DoWhileNode(cn);
        dwn.whileBlock = Block.parse(p, "dowhile-block");
        p.scopeIn("do-while-body");

        return dwn;
    }
}
