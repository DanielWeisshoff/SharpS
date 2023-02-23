package parser.parser.pointer;

import lexer.TokenType;
import parser.IdRegistry;
import parser.PError.UnimplementedError;
import parser.nodesystem.DataType;
import parser.nodesystem.node.data.var.DeclareNode;
import parser.nodesystem.node.data.var.pointer.PointerNode;
import parser.parser.Parser;
import parser.symboltable.VariableEntry;

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
