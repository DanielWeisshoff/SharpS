package com.danielweisshoff.parser.parser.loops;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.VarInitNode;
import com.danielweisshoff.parser.nodesystem.node.logic.conditions.ConditionNode;
import com.danielweisshoff.parser.nodesystem.node.loops.ForNode;
import com.danielweisshoff.parser.parser.Block;
import com.danielweisshoff.parser.parser.Bool;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.parser.VarDefinition;
import com.danielweisshoff.parser.parser.VarInitialization;

//root
//|
//|Block
//    | int i = 0
//    | while(i<10):
//        |...
//        |i = i+i

public class For {

    // KW_FOR ( INITIALIZATION , BOOL , DEFINITION ) : BLOCK
    public static ForNode parse(Parser p) {
        ForNode fn = new ForNode();

        p.assume(TokenType.KW_FOR, "Keyword FOR missing");
        p.assume(TokenType.O_ROUND_BRACKET, "Missing open bracket for for-loop");

        BlockNode ibn = new BlockNode(p.getScope() + 1, "for-init");
        p.scopeIn(ibn, "for-init");

        VarInitNode in = VarInitialization.parse(p);

        p.assume(TokenType.COMMA, "comma missing");
        ConditionNode cn = Bool.parse(p);

        p.assume(TokenType.COMMA, "comma missing");
        AssignNode an = VarDefinition.parse(p);

        p.assume(TokenType.C_ROUND_BRACKET, "Missing closed bracket for for-loop");
        p.assume(TokenType.COLON, "for-body-declarator missing");

        fn.init = in;
        fn.condition = cn;
        fn.assignment = an;

        fn.block = ibn;
        ibn.add(in);
        ibn.add(Block.parse(p, "for-body", true));

        return fn;
    }
}
