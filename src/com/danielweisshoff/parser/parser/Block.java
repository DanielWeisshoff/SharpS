package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.Node;

public class Block {

    public static BlockNode parse(Parser p, String name) {
        return parse(p, name, false);
    }

    public static BlockNode parse(Parser p, String name, boolean stayInScope) {

        int scope = stayInScope ? p.getScope() : p.getScope() + 1;
        BlockNode bn = new BlockNode(scope, name);
        p.scopeIn(bn, name);
        while (isInScope(p, scope) && !p.is(TokenType.EOF)) {
            Node instruction = Instruction.parse(p);
            bn.add(instruction);
        }
        p.scopeOut();

        return bn;
    }

    private static boolean isInScope(Parser p, int scope) {
        while (p.is(TokenType.NEWLINE))
            p.advance();

        System.out.println("tab?: " + p.curToken.type());
        int curScope = 0;
        if (p.is(TokenType.TAB)) {
            curScope = Integer.parseInt(p.curToken.value);
            p.advance();
        }
        //Rausscopen, falls noetig
        return (curScope == scope);
    }
}