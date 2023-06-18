package parser.parser;

import lexer.TokenType;
import parser.nodesystem.DataType;
import parser.nodesystem.node.data.var.DeclareNode;
import parser.symboltable.symbol.VariableSymbol;

public class VarDeclaration {

    public static DeclareNode parse(Parser p) {
        // PRIMITIVE ID
        TokenType keyword = p.curToken.type;
        DataType dataType = p.getPrimitiveType(keyword);
        p.eat();

        String name = p.curToken.value;
        p.eat(TokenType.IDENTIFIER);

        DeclareNode dn = new DeclareNode(name, dataType);

        VariableSymbol vs = new VariableSymbol(name, dataType);
        p.stm.define(vs);

        return dn;
    }
}