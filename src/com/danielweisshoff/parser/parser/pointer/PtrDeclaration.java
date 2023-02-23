package com.danielweisshoff.parser.parser.pointer;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.var.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.pointer.PointerNode;
import com.danielweisshoff.parser.parser.Parser;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class PtrDeclaration {

    public static DeclareNode parse(Parser p) {
        // PRIMITIVE PTR

        TokenType keyword = p.curToken.type();
        DataType dataType = p.getPrimitiveType(keyword);
        //TODO getting the primitive type of the pointer
        //DataType type = getPrimitiveType(keyword);

        p.advance();
        String name = Pointer.parse(p).name;
        //TODO gehört zur Semantik
        //schauen, ob variable schon existiert
        if (p.stm.lookupVariable(name)) {
            String error = "var '" + name + "': " + DataType.POINTER + " is already declared";
            new UnimplementedError(error, p.curToken);
        }

        //Variable eintragen
        long id = IdRegistry.newID();
        PointerNode pn = new PointerNode(name, "nullptr");
        VariableEntry ve = new VariableEntry(name, id, dataType);
        p.stm.addVariable(id, ve);

        //TODO wrong, ptr should have name,datatype and ptr type
        DeclareNode dn = new DeclareNode(name, DataType.POINTER);
        //p.addInstruction(dn);
        return dn;
    }
}
