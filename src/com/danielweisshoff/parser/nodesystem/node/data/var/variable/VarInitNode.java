package com.danielweisshoff.parser.nodesystem.node.data.var.variable;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.logger.Logger;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.DataType;
import com.danielweisshoff.parser.nodesystem.node.NodeType;
import com.danielweisshoff.parser.nodesystem.node.data.var.AssignNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.DeclareNode;
import com.danielweisshoff.parser.nodesystem.node.data.var.DefineNode;

// <PRIMITIVE> <ID> = <EXPR>
public class VarInitNode extends AssignNode {

    //TODO? Are all the attributes really needed?
    private final String name;
    public final DataType dataType;
    public boolean isPointer = false;
    private DeclareNode declaration;
    private DefineNode definition;

    public VarInitNode(DeclareNode declaration, DefineNode definition) {
        super(declaration.name, NodeType.INIT_NODE);

        this.name = declaration.name;
        this.dataType = declaration.dataType;
        this.declaration = declaration;
        this.definition = definition;

    }

    @Override
    public Data run() {
        declaration.run();
        definition.run();

        if (Interpreter.debug) {
            double value = Interpreter.instance.findVariable(name).asDouble();
            Logger.log("initializing variable " + name + " = " + value + " :" + dataType);
        }

        return new Data();
    }

    //TODO implementation
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);

        printAdvanced("name: " + name, depth + 1);
        printAdvanced(expression, depth + 1);
    }
}