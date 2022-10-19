package com.danielweisshoff.parser.parser;

import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.PError.UnimplementedError;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.data.assigning.PtrInitNode;
import com.danielweisshoff.parser.parser.pointer.Adress;
import com.danielweisshoff.parser.parser.pointer.Pointer;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class PtrInitialization {

    public static PtrInitNode parse(Parser p) {
        // PRIMITIVE PTR = ADRESS

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

        p.assume(TokenType.EQUAL, "= sign missing for ptr init");

        //TODO pretty messy
        String adress = Adress.parse(p);
        VariableEntry var = p.stm.findVariable(adress);
        //Variable eintragen
        long id = IdRegistry.newID();
        PtrInitNode pn = new PtrInitNode(name, p.getPrimitiveType(keyword), adress);
        VariableEntry ve = new VariableEntry(name, id, pn, dataType);

        p.stm.addVariable(id, ve);
        // p.addInstruction(pn);
        return pn;
    }
}
