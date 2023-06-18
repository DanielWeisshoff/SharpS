package parser.parser.pointer;

import parser.nodesystem.node.data.var.DeclareNode;
import parser.nodesystem.node.data.var.pointer.PtrInitNode;
import parser.parser.Parser;

public class PtrInitialization {

    // PRIMITIVE PTR = ADRESS
    public static PtrInitNode parse(Parser p) {

        //TODO implementation
        DeclareNode declare = PtrDeclaration.parse(p);

        //DefineNode define = PtrDefinition.parse(p);
        return new PtrInitNode(null, null, null);
    }
}
