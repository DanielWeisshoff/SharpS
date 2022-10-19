package com.danielweisshoff.interpreter;

import java.util.Stack;

import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.nodesystem.node.BlockNode;
import com.danielweisshoff.parser.nodesystem.node.binaryoperations.NumberNode;
import com.danielweisshoff.parser.symboltable.VariableEntry;

/*
TODO
?pointernode id und name werden komisch eingetragen
? findVariable in Interpreter auslagern?

*/
public class Interpreter {

    public static boolean debug = false;
    //when stepper is enabled, one line will be interpreted at a time on user input
    public static boolean stepper = true;
    // private Scanner stepperScanner = new Scanner(System.in);

    private Stack<StackFrame> stack = new Stack<>();

    public static Interpreter instance;

    public Interpreter() {
        if (Interpreter.instance == null)
            Interpreter.instance = this;
    }

    public Data interpret(BlockNode n) {
        return n.run();
    }

    public void newStackFrame() {

        StackFrame sf = new StackFrame();

        if (stack.size() != 0)
            sf.parent = stack.peek();
        stack.add(sf);
    }

    public void popStackFrame() {
        stack.pop();
    }

    public VariableEntry findVariable(String name) {
        return stack.peek().findVariable(name);
    }

    public void addVariable(String name, NumberNode node) {
        stack.peek().addVariable(name, node);
    }

    public void printStack() {

        StackFrame sf = stack.peek();

        if (sf == null)
            return;

        while (true) {
            for (VariableEntry ve : sf.variables)
                System.out.println(ve.getDescription() + " " + ve.node.data.asDouble());

            if (sf.parent != null) {
                sf = sf.parent;
                System.out.println("====================================");
            } else
                return;
        }
    }
}