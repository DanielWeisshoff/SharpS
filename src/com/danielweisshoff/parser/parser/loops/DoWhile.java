package com.danielweisshoff.parser.parser.loops;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;
import com.danielweisshoff.parser.nodesystem.node.loops.DoWhileNode;
import com.danielweisshoff.parser.parser.Block;
import com.danielweisshoff.parser.parser.Bool;
import com.danielweisshoff.parser.parser.Parser;

public class DoWhile {

    public static DoWhileNode parse(Parser p) {
        // do while ( BOOL ) : BLOCK
        p.assume(TokenType.KW_DO, "Keyword DO missing");
        p.assume(TokenType.KW_WHILE, "Keyword WHILE missing");
        p.assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for while-loop");

        ConditionNode cn = Bool.parse(p);

        p.assume(TokenType.C_ROUND_BRACKET, "Missing close bracket for while-loop");
        p.assume(TokenType.COLON, "while-body not defined");

        DoWhileNode dwn = new DoWhileNode(cn);
        dwn.whileBlock = Block.parse(p, "dowhile-block");
        //p.addInstruction(dwn);
        p.scopeIn("do-while-body");

        return dwn;
    }
}
