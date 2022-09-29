package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.VariableNode;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.DeclareNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class VarDeclaration {

    public static DeclareNode parse(Parser p) {
        TokenType keyword = p.curToken.type();
        p.advance();

        //getting the primitive type
        DataType type = p.getPrimitiveType(keyword);

        String name = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "Fehler beim Deklarieren einer Variable");
        DeclareNode dn = new DeclareNode(name, type);

        //TODO gehört zur Semantik
        //schauen, ob variable schon existiert
        if (p.stm.lookupVariable(name)) {
            String error = "var '" + name + "': " + type + " is already declared";
            new UnimplementedError(error, p.curToken);
        }

        //Variable eintragen
        long id = IdRegistry.newID();
        VariableNode vn = new VariableNode(name, type);
        vn.data = new Data();

        VariableEntry ve = new VariableEntry(name, id, vn);
        p.stm.addVariable(id, ve);
        //p.addInstruction(dn);
        return dn;
    }
}