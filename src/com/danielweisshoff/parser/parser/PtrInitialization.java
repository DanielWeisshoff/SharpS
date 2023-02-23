package com.danielweisshoff.parser.parser;

import com.danielweisshoff.parser.nodesystem.node.data.var.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.pointer.PtrInitNode;
import com.danielweisshoff.parser.parser.pointer.PtrDeclaration;

public class PtrInitialization {

    // PRIMITIVE PTR = ADRESS
    public static PtrInitNode parse(Parser p) {

        //TODO implementation
        DeclareNode declare = PtrDeclaration.parse(p);

        //DefineNode define = PtrDefinition.parse(p);
        return new PtrInitNode(null, null, null);
    }
}
