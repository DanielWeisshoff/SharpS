package com.danielweisshoff.parser.nodesystem.node;

import java.util.ArrayList;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.Data;

public class OutNode extends Node {

    ArrayList<Token> token;

    public OutNode(ArrayList<Token> args) {
        super(null, null, NodeType.OUT_NODE);
        this.token = args;
    }

    @Override
    public Data run() {
        StringBuilder output = new StringBuilder();

        for (Token t : token) {

            if (t.type() == TokenType.IDENTIFIER) {
                Data data = Interpreter.instance.findVariable(t.value);
                if (data == null) {
                    System.out.println("var '" + t.value + "' not found");
                    System.exit(1);
                } else
                    output.append(data.asDouble());
            } else if (t.isNumeric())
                output.append(Double.parseDouble(t.value));
            else if (t.type() == TokenType.STRING)
                output.append(t.value);
            else if (t.type() == TokenType.COMMA)
                output.append(" ");
            else
                output.append("can't output type '" + t.type() + "'");
        }
        System.out.println(">> " + output.toString());
        return new Data();
    }

    //TODO implementation 2.0
    @Override
    public void print(int depth) {
        System.out.println(offset(depth) + nodeType);
        for (Token t : token) {
            if (t.value == "" || t.value == null)
                printAdvanced("" + t.type(), depth + 1);
            else
                printAdvanced(t.value + " : " + t.type(), depth + 1);
        }
    }
}
