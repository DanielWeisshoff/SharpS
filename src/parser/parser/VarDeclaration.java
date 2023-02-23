package parser.parser;

import lexer.TokenType;
import parser.IdRegistry;
import parser.PError.UnimplementedError;
import parser.nodesystem.DataType;
import parser.nodesystem.node.data.var.DeclareNode;
import parser.symboltable.VariableEntry;

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