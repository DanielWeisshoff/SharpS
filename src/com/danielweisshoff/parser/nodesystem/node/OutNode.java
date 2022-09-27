package com.danielweisshoff.parser.nodesystem.node;

import com.danielweisshoff.interpreter.Interpreter;
import com.danielweisshoff.lexer.Token;
import com.danielweisshoff.lexer.TokenType;
import com.danielweisshoff.parser.nodesystem.Data;
import com.danielweisshoff.parser.symboltable.VariableEntry;

public class OutNode extends Node {

    Token output;

    public OutNode(Token output) {
        super(null, null, NodeType.OUT_NODE);
        this.output = output;
    }

    @Override
    public Data run() {
        String outputStr = "";

        if (output.type() == TokenType.IDENTIFIER) {
            VariableEntry var = Interpreter.stm.findVariable(output.value);
            outputStr = "" + var.node.data.asDouble();
        } else if (output.isNumeric())
            outputStr = "" + Double.parseDouble(output.value);

        System.out.println(">> " + outputStr);
        return new Data();
    }
}
