package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class Block {

    public static BlockNode parse(Parser p, String name) {

        BlockNode bn = new BlockNode(name);
        p.scopeIn(name);

        p.BOI();
        while (!p.is(TokenType.EOF) && isInScope(p)) {
            Node instruction = Instruction.parse(p);
            bn.add(instruction);
        }
        p.scopeOut();
        return bn;
    }

    private static boolean isInScope(Parser p) {

        if (p.curInstructionScope > p.curScope) {
            new UnimplementedError("wrong indentation", p.curToken);
        }
        return (p.curInstructionScope == p.curScope);
    }
}