package parser.nodesystem.node.data.var.pointer;

import interpreter.Interpreter;
import parser.PError.UnimplementedError;
import parser.nodesystem.data.Data;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.primitives.NumberNode;

/*
Saves the name of a vatiable and searches for its id at runtime
*/
public class PointerNode extends NumberNode {

    public String adress;

    public final String name;

    public PointerNode(String name, String adress) {
        super(null, null, NodeType.POINTER_NODE);
        this.name = name;
        this.adress = adress;
    }

    @Override
    public Data run() {

        Data data = Interpreter.instance.findVariable(adress);
        //TODO in the end this will point to some adress anyway
        if (data == null)
            new UnimplementedError("Interpreter Error: adress '" + adress + "' empty");

        return data;
    }

    @Override
    public void print() {
        super.print();
    }
}
