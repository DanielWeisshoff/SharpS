package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.var.DeclareNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class VarDeclaration {

    public static DeclareNode parse(Parser p) {
        // PRIMITIVE ID
        TokenType keyword = p.curToken.type();
        DataType dataType = p.getPrimitiveType(keyword);
        p.advance();

        String name = p.curToken.value;
        p.assume(TokenType.IDENTIFIER, "Fehler beim Deklarieren einer Variable");

        DeclareNode dn = new DeclareNode(name, dataType);

        //TODO gehört zur Semantik
        //schauen, ob variable schon existiert
        if (p.stm.lookupVariable(name)) {
            String error = "var '" + name + "': " + dataType + " is already declared";
            new UnimplementedError(error, p.curToken);
        }

        //Variable eintragen
        long id = IdRegistry.newID();

        VariableEntry ve = new VariableEntry(name, id, dataType);
        p.stm.addVariable(id, ve);

        //p.addInstruction(dn);
        return dn;
    }
}