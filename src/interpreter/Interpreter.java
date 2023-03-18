package interpreter;

import java.util.Stack;

import parser.nodesystem.Data;
import parser.nodesystem.node.diverse.FunctionNode;

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

    public Data interpret(FunctionNode function) {
        return function.run();
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

    public Data findVariable(String name) {
        return stack.peek().getVariable(name);
    }

    public void addVariable(String name, Data data) {
        stack.peek().addVariable(name, data);
    }

    public void printStack() {
        StackFrame sf = stack.peek();

        if (sf == null)
            return;

        while (true) {
            for (Data ve : sf.getVariables())
                System.out.println(ve.dataType + " " + ve.asDouble());

            if (sf.parent != null) {
                sf = sf.parent;
                System.out.println("====================================");
            } else
                return;
        }
    }
}