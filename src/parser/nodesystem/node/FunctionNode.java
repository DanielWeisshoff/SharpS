package parser.nodesystem.node;

import interpreter.Interpreter;
import parser.nodesystem.Data;

public class FunctionNode extends Node {

    public String name;
    public BlockNode block;

    public FunctionNode(String name) {
        super(null, null, NodeType.FUNCTION_NODE);
        this.name = name;
    }

    @Override
    public Data run() {
        //DEBUG

        Interpreter.instance.newStackFrame();

        System.out.println("calling function " + name + "()");
        return block.run();
    }

    @Override
    public void print(int depth) {
        block.print(depth);
    }

    public void add(Node n) {
        block.add(n);
    }
}
