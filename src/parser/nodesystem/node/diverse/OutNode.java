package parser.nodesystem.node.diverse;

import java.util.ArrayList;

import interpreter.Interpreter;
import lexer.Token;
import lexer.TokenType;
import parser.nodesystem.data.Data;
import parser.nodesystem.data.VoidPtr;
import parser.nodesystem.data.numerical.Numerical;
import parser.nodesystem.node.Node;
import parser.nodesystem.node.NodeType;

public class OutNode extends Node {

    ArrayList<Token> token;

    public OutNode(ArrayList<Token> args) {
        super(null, null, NodeType.OUT_NODE);
        this.token = args;
    }

    //TODO sollte alles zulassen, was einen Rückgabewert hat
    @Override
    public Data run() {
        StringBuilder output = new StringBuilder();

        for (Token t : token) {

            if (t.type == TokenType.IDENTIFIER) {
                Data data = Interpreter.instance.findVariable(t.value);
                if (data == null) {
                    System.out.println("var '" + t.value + "' not found");
                    System.exit(1);
                } else
                    output.append(((Numerical) data).value);
            } else if (t.isNumeric())
                output.append(Double.parseDouble(t.value));
            else if (t.type == TokenType.STRING)
                output.append(t.value);
            else if (t.type == TokenType.COMMA)
                output.append(" ");
            else
                output.append("can't output type '" + t.type + "'");
        }
        System.out.println(">> " + output.toString());
        return new VoidPtr();
    }

    @Override
    public void print() {
        super.print();
    }
}