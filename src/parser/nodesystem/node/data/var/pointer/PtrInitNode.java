package parser.nodesystem.node.data.var.pointer;

import parser.nodesystem.DataType;
import parser.nodesystem.data.Data;
import parser.nodesystem.data.numerical.integer.Bool;
import parser.nodesystem.node.NodeType;
import parser.nodesystem.node.data.var.AssignNode;

//TODO NumberNode.data is unused!!!
public class PtrInitNode extends AssignNode {

    private final String name;
    public final DataType dataType;
    public final String adress;
    public boolean isPointer = false;

    public PtrInitNode(String name, DataType dataType, String adress) {
        super(name, NodeType.PTR_INIT_NODE);

        this.name = name;
        this.dataType = dataType;
        this.adress = adress;
    }

    @Override
    public Data run() {
        //TODO
        // PointerNode pn = new PointerNode(name, adress);
        // Interpreter.instance.addVariable(name, dataType);

        // if (Interpreter.debug) {
        //     Data data = Interpreter.instance.findVariable(adress).node.run();
        //     Logger.log(data.value + ", " + dataType);
        // }

        return new Bool(true);
    }

    @Override
    public void print() {
        super.print();
    }
}