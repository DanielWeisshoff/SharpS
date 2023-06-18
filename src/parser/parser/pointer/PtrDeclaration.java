package parser.parser.pointer;

import lexer.TokenType;
import parser.nodesystem.DataType;
import parser.nodesystem.node.data.var.DeclareNode;
import parser.nodesystem.node.data.var.pointer.PointerNode;
import parser.parser.Parser;
import parser.symboltable.symbol.Symbol;
import parser.symboltable.symbol.VariableSymbol;

public class PtrDeclaration {

    public static DeclareNode parse(Parser p) {
        // PRIMITIVE PTR

        TokenType keyword = p.curToken.type;
        DataType dataType = p.getPrimitiveType(keyword);
        //TODO getting the primitive type of the pointer
        //DataType type = getPrimitiveType(keyword);

        p.eat();
        String name = Pointer.parse(p).name;

        PointerNode pn = new PointerNode(name, "nullptr");
        Symbol s = new VariableSymbol(name, dataType);
        p.stm.define(s);

        //Todo
        //return new DeclareNode(name, new PrimitivePointerType(dataType));
        return null;
    }
}
