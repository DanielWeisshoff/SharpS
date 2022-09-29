package com.danielweisshoff.parser.nodesystem.node.data.assigning;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.IdRegistry;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.PointerNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

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

        //generate an id for the variable
        long id = IdRegistry.newID();

        PointerNode pn = new PointerNode(name, adress, dataType);
        VariableEntry entry = new VariableEntry(name, id, pn);
        Interpreter.stm.addVariable(id, entry);

        if (Interpreter.debug) {
            Data data = Interpreter.stm.findVariable(adress).node.run();
            Logger.log(data.data + ", " + dataType);
        }

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(nodeType);
    }

}
